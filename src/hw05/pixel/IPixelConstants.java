package hw05.pixel;

/**
 * Represents the interface for constant values that are used for pixels. This interface allows
 * for constant variable to be enforced globally by any class that decides to inherit this
 * interface and utilizes the constant values available.
 */
public interface IPixelConstants {

  /**
   * Represents the min value that a specific type of value can be.
   */
  int MIN_VALUE = 0;

  /**
   * Represents the max value that a specific type of value can be.
   */
  int MAX_VALUE = 255;
}
