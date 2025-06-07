public class Utils {

  public static String exerciseHeader(Integer exercNumber) {
    StringBuilder builder = new StringBuilder();
    String chainPattern = "=-=-=-=-=-=-=-";
    builder.append(chainPattern);
    builder.append(String.format("\tExercise: %d\t", exercNumber));
    builder.append(chainPattern);

    return builder.toString();
  }
  
}
