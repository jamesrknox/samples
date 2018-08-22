# Introduction

This example contains the following :

* EventFlow hello world application that simply writes input data to log adapter

![Application](https://raw.githubusercontent.com/plord12/samples/master/fragments/eventflow/helloworld/src/site/resources/images/HelloWorld.png)

* [Junit test case](../../test/java/com/tibco/ep/samples/helloworld/TestCase.java)

```java
    /**
     * test case
     * 
     * @throws StreamBaseException on error
     */
    @Test
    public void test1() throws StreamBaseException {
        LOGGER.info("Test Case 1");
        String inputTupleAsJSONString = "{'data':'Hello World'}";
        server.getEnqueuer("InputStream").enqueue(JSONSingleQuotesTupleMaker.MAKER, inputTupleAsJSONString);
    }
```

