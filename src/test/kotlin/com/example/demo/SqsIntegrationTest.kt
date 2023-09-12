package com.example.demo

import com.example.demo.SqsIntegrationTest.TestConfig.SqsUrlInitializer
import io.awspring.cloud.core.region.StaticRegionProvider
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.DescribeSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.web.WebAppConfiguration
import org.testcontainers.containers.localstack.LocalStackContainer
import org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.regions.providers.AwsRegionProvider

@Testcontainers
@DirtiesContext
@WebAppConfiguration
@ContextConfiguration(initializers = [SqsUrlInitializer::class])
@SpringBootTest(
  classes = [DemoApplication::class, SqsIntegrationTest.TestConfig::class],
  properties = ["spring.main.allow-bean-definition-overriding=true"],
)
class SqsIntegrationTest : DescribeSpec() {

  @Autowired
  lateinit var publisher: Publisher

  @Value("\${cloud.aws.sqs.endpoint}")
  lateinit var endpoint: String

  init {

    it("should publish and read message") {
      shouldNotThrow<Exception> {
        publisher.publish()
      }
    }
  }

  @TestConfiguration
  class TestConfig {

    @Bean
    @Primary
    fun awsCredentialsProvider(): AwsCredentialsProvider {
      if (!localStack.isRunning) localStack.start()
      val creds = AwsBasicCredentials.create(localStack.accessKey, localStack.secretKey)
      return StaticCredentialsProvider.create(creds)
    }

    @Bean
    @Primary
    fun awsRegionProvider(): AwsRegionProvider {
      return StaticRegionProvider(Region.EU_CENTRAL_1.id())
    }

    /**
     * Just configures Localstack's SQS server endpoint in the application
     */
    companion object {

      @JvmStatic
      val localStack: LocalStackContainer = LocalStackContainer(DockerImageName.parse("localstack/localstack:latest"))
        .withServices(SQS)
    }

    class SqsUrlInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

      override fun initialize(applicationContext: ConfigurableApplicationContext) {
        localStack.start()
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "single-queue")
        localStack.execInContainer("awslocal", "sqs", "create-queue", "--queue-name", "list-queue")
        TestPropertyValues.of("cloud.aws.sqs.endpoint=${localStack.getEndpointOverride(SQS)}")
          .applyTo(applicationContext.environment)
      }
    }
  }
}
