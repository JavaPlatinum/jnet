package com.jbrown.jnet.commands.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import com.jbrown.jnet.core.RequestI;

public class WGetAction extends AbstractAction<String> {

  public WGetAction(RequestI request) {
    super(request);
  }

  @Override
  String perform() {
    String[] parameters = _request.getParameters();
    String response = callURL(parameters[0]);
    return response;
  }

  @Override
  boolean validate() {
    return true;
  }

  private String wget(String[] parameters) {
    String result = "URL not found";
    try {
      URL url = new URL(parameters[0]);
      StringBuilder str = new StringBuilder();

      try (BufferedReader reader = new BufferedReader(new InputStreamReader(
          url.openStream(), "UTF-8"))) {
        for (String line; (line = reader.readLine()) != null;) {
          str.append(line);
        }

        result = str.toString();
      }
    } catch (Exception ex) {
      result = ex.getMessage();
    }

    return result;
  }
  
  public String callURL(String myURL) {
     
    StringBuilder sb = new StringBuilder();
    URLConnection urlConn = null;
    InputStreamReader in = null;
    try {
      URL url = new URL(myURL);
      urlConn = url.openConnection();
      if (urlConn != null)
        urlConn.setReadTimeout(60 * 1000);
      if (urlConn != null && urlConn.getInputStream() != null) {
        in = new InputStreamReader(urlConn.getInputStream(),
            Charset.defaultCharset());
        BufferedReader bufferedReader = new BufferedReader(in);
        if (bufferedReader != null) {
          int cp;
          while ((cp = bufferedReader.read()) != -1) {
            sb.append((char) cp);
          }
          bufferedReader.close();
        }
      }
    in.close();
    } catch (Exception e) {
      sb.append(e.getMessage());
    } 
 
    return sb.toString();
  }
}
