package pss.bsp.monitor;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadStats {
  private final Map<String, Long> taskTimes = new ConcurrentHashMap<>();
  private final Map<String, Long> companyTimes = new ConcurrentHashMap<>();
  private String currentCompany = "DEFAULT"; // Valor por defecto si no hay compañía

  private long currentStart = 0;
  private String currentTask = "Idle";
  private final String threadName;

  public ThreadStats(String threadName) {
      this.threadName = threadName;
  }

  public synchronized void start(String company, String task) {
    stop(); // registra el tiempo anterior
    currentTask = task;
    currentCompany = company != null ? company : "DEFAULT";
    currentStart = System.currentTimeMillis();
}


  public synchronized void stop() {
    if (currentStart > 0) {
        long elapsed = System.currentTimeMillis() - currentStart;

        // Por tarea
        taskTimes.merge(currentTask, elapsed, Long::sum);
        // Por compañía
        companyTimes.merge(currentCompany, elapsed, Long::sum);
    }
    currentTask = "Idle";
    currentCompany = "DEFAULT";
    currentStart = System.currentTimeMillis();
}

  public synchronized Map<String, Long> getSnapshotAndReset() {
      stop();
      Map<String, Long> copy = new HashMap<>(taskTimes);
      taskTimes.clear();
      return copy;
  }
  public synchronized Map<String, Long> getCompanySnapshotAndReset() {
    stop();
    Map<String, Long> copy = new HashMap<>(companyTimes);
    companyTimes.clear();
    return copy;
}
  public synchronized Map<String, Long> getSnapshotIncludingCurrent() {
    Map<String, Long> snapshot = new HashMap<>(taskTimes);
    if (currentStart > 0 && currentTask != null) {
        long now = System.currentTimeMillis();
        long elapsed = now - currentStart;
        snapshot.merge(currentTask, elapsed, Long::sum);
    }
    return snapshot;
}
  public synchronized Map<String, Long> getCompanySnapshotIncludingCurrent() {
    Map<String, Long> snapshot = new HashMap<>(companyTimes);
    if (currentStart > 0 && currentCompany != null) {
        long now = System.currentTimeMillis();
        long elapsed = now - currentStart;
        snapshot.merge(currentCompany, elapsed, Long::sum);
    }
    return snapshot;
}


  public String getThreadName() {
      return threadName;
  }
  public synchronized String getCompanyStatusLog() {
    StringBuilder sb = new StringBuilder();
    sb.append("Tiempos acumulados por compañía:");

    companyTimes.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(entry -> sb.append(
            String.format("   %-10s : %8d ms|", entry.getKey(), entry.getValue()))
        );

    return sb.toString();
}
  public synchronized String getSnapshotAsJson() {
    stop(); // detener la tarea actual antes de tomar el snapshot

    StringBuilder sb = new StringBuilder();
    sb.append("{\n");
    sb.append(String.format("  \"thread\": \"%s\",\n", threadName));

    // Tareas
    sb.append("  \"tasks\": [\n");
    taskTimes.entrySet().stream()
        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(entry -> sb.append(String.format(
            Locale.US,
            "    { \"task\": \"%s\", \"ms\": %d },\n",
            entry.getKey(), entry.getValue()))
        );
    // Eliminar la última coma
    if (!taskTimes.isEmpty()) sb.setLength(sb.length() - 2);
    sb.append("\n  ],\n");


    sb.append("}");
    return sb.toString();
}

  public synchronized String getFullStatusLog() {
    
    StringBuilder sb = new StringBuilder();
    sb.append("Tiempos");

    taskTimes.entrySet().stream()
    		.sorted(Map.Entry.<String, Long>comparingByValue().reversed())
        .forEach(entry -> sb.append(
            String.format("   %-25s : %8d ms|", entry.getKey(), entry.getValue()))
        );

    return sb.toString();
}


}
