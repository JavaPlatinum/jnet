package com.jbrown.jnet.core;

import java.net.Socket;
import com.jbrown.jnet.utils.JsonMap;
import com.jbrown.jnet.utils.KeysI;
import static com.jbrown.jnet.utils.StringUtils.*;
import static java.lang.String.format;

public class Request implements RequestI {
  private Socket _socket;
  private String   _rowInput;
  private Command  _command;
  private String[] _parameters;
  private SharedContextI _context;
  private final JsonMap _jsonMap;

  public Request(Socket socket, String rowInput) {
    _socket = socket;
    _rowInput = rowInput;
    _command = populateCommand();
    _parameters = populateParameters();
    _jsonMap = new JsonMap();
  }

  @Override
  public SharedContextI getContext() {
    return _context;
  }

  @Override
  public void setContext(SharedContextI context) {
    _context = context;
  }

  @Override
  public Command getCommand() {
    return _command;
  }

  @Override
  public String[] getParameters() {
    return _parameters;
  }

  @Override
  public String getRowCommand() {
    return _rowInput;
  }

  @Override
  public Socket getSocket() {
    return _socket;
  }

  private Command populateCommand() {
    try {
      String[] rowInput = beautifyRowInput();

      return Command.find(rowInput[0]);

    } catch (Exception ex) {
      System.out.printf("bad command => %s]", _rowInput);
    }

    return null;
  }

  private String[] populateParameters() {
    String[] rowInput = beautifyRowInput();
    String[] parameters = new String[0];

    for (int i = 1; i < rowInput.length; i++) {
      parameters = arrayPush(parameters, rowInput[i]);
    }

    return parameters;
  }

  /**
   * Remove null and empty chars from the row input from the user
   */
  private String[] beautifyRowInput() {
    String[] cleanArray = new String[0];

    try {
      String[] strs = _rowInput.trim().split(KeysI.SPACE_K);

      if (strs != null && strs.length > 0) {
        for (int i = 0; i < strs.length; i++) {
          if (!isEmpty(strs[i].trim())) {
            cleanArray = arrayPush(cleanArray, strs[i].trim());
          }
        }
      }
    } catch (Exception ex) {
      System.out.printf("bad command %s", _rowInput);
    }

    return cleanArray;
  }

  @Override
  public JsonMap getJsonMap() {
    return _jsonMap;
  }

  @Override
  public String getRowParamsExcludingCommand(){
    String line = _rowInput;
    String value = null;
    String[] arr = line.split(" ");

    if(arr != null && arr.length > 1){
      String command = arr[0];
      value = line.replaceAll( format(".*\\b%s\\b", command), "" );
    }

    return value;
  }
}
