import java.io.IOException;

/**
 * This a class meant to simulate an IOException. The purpose of this is to see if renderBoard and
 * renderMessage in view correctly catch an IOException. The implementation of the methods in this
 * class serve no purpose other than to throw IOExceptions.
 */
public class IOExceptionSimulation implements Appendable {

  /**
   * Throws an exception whenever the input length is greater than 0. Designed to make it easy to
   * throw an IOException for testing purposes.
   *
   * @param csq the CharSequence attempting to be appended to this appendable
   * @return null
   * @throws IOException if input length is greater than 0
   */
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    if (csq.length() > 0) {
      throw new IOException();
    }
    return null;
  }

  /**
   * Throws an exception whenever the input length is null. Designed to make it easy to throw an
   * IOException for testing purposes.
   *
   * @param csq   the CharSequence attempting to be appended to this appendable
   * @param start the starting index for the given CharSequence
   * @param end   the ending index for the given CharSequence
   * @return null
   * @throws IOException if input is null
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    if (csq == null) {
      throw new IOException();
    }
    return null;
  }

  /**
   * Throws an exception whenever the input is an empty character. Designed to make it easy to throw
   * an IOException for testing purposes.
   *
   * @param c the character attempting to be appended to this appendable
   * @return null
   * @throws IOException if input is an empty character
   */
  @Override
  public Appendable append(char c) throws IOException {
    if (c == ' ') {
      throw new IOException();
    }
    return null;
  }
}
