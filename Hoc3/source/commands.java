public interface Command{
  String commandDescription();
  String execute();
  String operationDescription();
  void handleParameters(String parameters);
}
