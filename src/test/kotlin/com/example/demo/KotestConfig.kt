package com.example.demo

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.extensions.spring.SpringExtension

class KotestConfig : AbstractProjectConfig() {

  override val isolationMode = IsolationMode.InstancePerLeaf
  override fun extensions() = listOf(SpringExtension)
}
