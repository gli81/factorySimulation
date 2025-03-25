package edu.duke.ece651.group6.factorySimulation.DataModel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InvalidInputExceptionTest {
  @Test
  public void testConstructmessage() {
    String e = "Invalid input test message";
    InvalidInputException excp = new InvalidInputException(e);
    assertEquals(e, excp.getMessage());
    assertNull(excp.getCause());
  }

  @Test
  public void testCause() {
    String e = "Invalid input with cause";
    Throwable cause = new RuntimeException("Original error");
    InvalidInputException except = new InvalidInputException(e, cause);

    assertEquals(e, except.getMessage());
    assertEquals(cause, except.getCause());
    assertEquals("Original error", except.getCause().getMessage());
  }

}
