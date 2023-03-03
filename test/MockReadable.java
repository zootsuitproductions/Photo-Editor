import java.io.IOException;
import java.nio.CharBuffer;

/**
 * Mock class for testing the Controller. Throws an exception for its method.
 */
public class MockReadable implements Readable {

  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException();
  }
}
