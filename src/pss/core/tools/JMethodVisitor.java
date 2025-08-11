package pss.core.tools;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
/**
 * Insert the type's description here.
 * Creation date: (26/02/2002 22:05:43)
 * @author: Administrator
 */
public class JMethodVisitor {
  private static JMethodVisitor instance;
  private HashMap<Object, HashMap<String, Integer>> visitedMap = new HashMap<Object, HashMap<String, Integer>>(89);
  private HashMap<Object, PrintWriter> outs = new HashMap<Object, PrintWriter>(89);
  private boolean logOnlyMethod = false;
/**
 * MethodVisitor constructor comment.
 */
private JMethodVisitor() {
  super();
}
/**
 * MethodVisitor constructor comment.
 */
public static JMethodVisitor getInstance() {
  if (instance==null) {
    instance=new JMethodVisitor();
  }
  return instance;
}

public PrintWriter getOut(Object anObject) {
  PrintWriter out = this.outs.get(anObject);
  if (out==null) {
    out = new PrintWriter(System.out, true);
    this.setOut(anObject, out);
  }
  return out;
}
public void setOut(Object anObject, PrintWriter out) {
  this.outs.put(anObject, out);
}
  public boolean isLogOnlyMethod() {
    return logOnlyMethod;
  }
  public void setLogOnlyMethod(boolean logOnlyMethod) {
    this.logOnlyMethod = logOnlyMethod;
  }


private void logVisit(Object anObjectToVisit, String aVisitedMethod) {
  // ensure the map for the object
  HashMap<String, Integer> mapForObject = this.visitedMap.get(anObjectToVisit);
  if (mapForObject==null) {
    mapForObject = new HashMap<String, Integer>();
    this.visitedMap.put(anObjectToVisit, mapForObject);
  }
  // ensure and get the count
  Integer count = mapForObject.get(aVisitedMethod);
  if (count==null) {
    count = new Integer(0);
  }
  count = new Integer(count.intValue()+1);
  mapForObject.put(aVisitedMethod, count);
  if (this.isLogOnlyMethod()) {
    this.getOut(anObjectToVisit).println("("+count+"): " + aVisitedMethod);
  } else {
    this.getOut(anObjectToVisit).println("[Method Visitor] visiting method ("+count+"): " + aVisitedMethod);
  }
}
public void resetVisits(Object anObjectToVisit) {
  this.visitedMap.remove(anObjectToVisit);
  this.outs.remove(anObjectToVisit);
}

public void visit(Object anObjectToVisit) {
  this.visit(anObjectToVisit, 1);
}
public void visit(Object anObjectToVisit, int zBackInStack) {
  try {
    StringWriter sw = new StringWriter();
    PrintWriter writer = new PrintWriter(sw);
    new Exception("Stack trace").printStackTrace(writer);
    BufferedReader reader = new BufferedReader(new StringReader(sw.getBuffer().toString()));
    String line = reader.readLine();
    line = reader.readLine();
    line = reader.readLine();
    for (int i = 0; i < zBackInStack; i++) {
      line = reader.readLine();
    }
    line = line.trim();
    String className = line.substring(line.indexOf(' ')+1, line.lastIndexOf('.'));
    String methodName = line.substring(line.lastIndexOf('.')+1);
    this.logVisit(anObjectToVisit, className + "#" + methodName);
//    System.out.println(sw.getBuffer());
  } catch (Exception e) {
    System.out.println("[Method Visitor] could not guess visiting method");
  }
}
}
