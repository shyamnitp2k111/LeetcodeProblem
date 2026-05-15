import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectionPool {

    //collection pool class
    // getConnection

    private CopyOnWriteArrayList<Connection> list;
    private int size;

    public ConnectionPool(CopyOnWriteArrayList<Connection> list, int size) {
        this.list = list;
        this.size = size;
    }

    public int getConnection() throws InterruptedException {
        for(int index = 0 ; index < list.size(); index++) {

            synchronized (ConnectionPool.class) {
                if (list.get(index).getStatus().equals("Available")) {
                    Thread.sleep(100);
                    list.get(index).setStatus("Using");
                    return list.get(index).getConnectionId();
                }
            }
        }

        return -1;
    }




}
class Connection{
    private String status;
    private int connectionId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
