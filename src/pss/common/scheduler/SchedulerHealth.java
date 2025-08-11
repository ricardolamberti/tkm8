package pss.common.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SchedulerHealth {

    // Clase interna que representa la información de cada proceso
    private static class ProcessInfo {
        private String status;
        private String description;
        private String activity;

        public ProcessInfo(String status, String description) {
            this.status = status;
            this.description = description;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
        
        public String getDescription() {
            return description;
        }
        
        public String getActivity() {
					return activity;
				}

				public void setActivity(String activity) {
					this.activity = activity;
				}

				public void setDescription(String description) {
            this.description = description;
        }
    }
    static Map<Long,Long> threadRegiser = new HashMap<Long,Long>();

    // Mapa concurrente para almacenar la información de cada proceso (ID -> ProcessInfo)
    private static ConcurrentMap<Long, ProcessInfo> processStatus = new ConcurrentHashMap<>();

    // Establece o actualiza el estado de un proceso
    public static void setProcessStatus(Long processId, String status) {
        ProcessInfo info = processStatus.get(processId);
        if (info == null) {
            info = new ProcessInfo(status, ""); // Sin descripción inicial
            processStatus.put(processId, info);
        } else {
            info.setStatus(status);
        }
        threadRegiser.put(Thread.currentThread().getId(), processId);
   }

    // Establece o actualiza la descripción de un proceso
    public static void setProcessDescription(Long processId, String description) {
        ProcessInfo info = processStatus.get(processId);
        if (info == null) {
            info = new ProcessInfo("", description); // Sin estado inicial
            processStatus.put(processId, info);
        } else {
            info.setDescription(description);
        }
    }

    public static void setProcessActivity(Long threadId, String activity) {
    	Long processId = threadRegiser.get(threadId);
    	if (processId==null) return;
      ProcessInfo info = processStatus.get(processId);
      if (info == null) {
          info = new ProcessInfo("", activity); // Sin estado inicial
          processStatus.put(processId, info);
      } else {
          info.setActivity(activity);
      }
  }
    // Remueve la información de un proceso del mapa (por ejemplo, al finalizarlo)
    public static void removeProcessStatus(Long processId) {
        processStatus.remove(processId);
    }

    // Genera un reporte con el estado y la descripción de todos los procesos
    public static String getStatusReport() {
        StringBuilder sb = new StringBuilder();
        for (Long pid : processStatus.keySet()) {
            ProcessInfo info = processStatus.get(pid);
            sb.append("Process ").append(pid)
            .append(", Description: ").append(info.getDescription())
              .append(" => Status: ").append(info.getStatus())
              .append(" (").append(info.getActivity()).append(")")
               .append("\n");
        }
        return sb.toString();
    }
}
