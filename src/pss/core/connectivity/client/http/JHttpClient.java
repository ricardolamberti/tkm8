package pss.core.connectivity.client.http;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;


public class JHttpClient {


  static {
    System.getProperties().setProperty( "org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog" );
  };
//------------------------------------------------------------------------------
// Object Variables
//------------------------------------------------------------------------------
  //private boolean bConnected = false;
  private String oUrl;
  private PostMethod post;
  HttpClient httpclient;

//------------------------------------------------------------------------------
// Class Constructors
//------------------------------------------------------------------------------
  public JHttpClient() throws Exception {
  }

//------------------------------------------------------------------------------
// Getters & Setters
//------------------------------------------------------------------------------
  public String getUrl() { return oUrl.toString(); }
  public void setUrl( String sUrl ) throws Exception {
    oUrl = sUrl;
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean connect() throws Exception {
    //bConnected = true;

    post = new PostMethod(oUrl);
    httpclient = new HttpClient();
    httpclient.setConnectionTimeout( 30000 );
    httpclient.setTimeout( 30000 );
    post.setRequestHeader( "Content-Type", "application/x-www-form-urlencoded" );

    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized boolean write( String zMsg ) throws Exception {
    post.setRequestBody(zMsg);
    return true;
  }
//----------------------------------------------------------------------------------------------------------------------
  public synchronized String recv() throws Exception {
    //int result = httpclient.executeMethod(post);
    // Display status code
//    System.out.println("Response status code: " + result);
    // Display response
//    System.out.println("Response body: ");
//    System.out.println(post.getResponseBodyAsString());
    return post.getResponseBodyAsString();
  }
//----------------------------------------------------------------------------------------------------------------------
  public boolean disconnect() throws Exception {
    if( post != null ) {
      post.releaseConnection();
    }
    return true;
  }
}
