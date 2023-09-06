# demo-sqs

## Problem

When you have a List of Items as a payload the deserialization will throw an `MessageConversionException`

```
com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `com.example.demo.Item` from Array value (token `JsonToken.START_ARRAY`)
 at [Source: (String)"[{foo=foo, bar=bar}, {foo=foo, bar=null}, {foo=null, bar=bar}]"; line: 1, column: 1]
```

## Setup

This is a simple app, which has 2 endpoints to send a message to a queue with either a single item or a list of items. Furthermore there is a Listener which listens to both queues and tries to process
them.

### Send single item

When you use `/send-single` a random item is sent to SQS. This works fine.

`curl -X POST --location "http://localhost:8080/send-single"`

### Send list of items

When you use `/send-list` a list with a few items is sent to SQS. This ends up in the error

`curl -X POST --location "http://localhost:8080/send-list"`

