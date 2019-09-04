package response;

/**
 * @description:
 * @author: gaoweiwei_v
 * @time: 2019/8/6 10:27 AM
 */
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExecuteResult {
    public int getExitCode() {
        return exitCode;
    }

    public void setExitCode(int exitCode) {
        this.exitCode = exitCode;
    }

    public String getExecuteOut() {
        return executeOut;
    }

    public void setExecuteOut(String executeOut) {
        this.executeOut = executeOut;
    }

    private int exitCode;
    private String executeOut;

    public ExecuteResult(int exitCode, String executeOut) {
        this.exitCode = exitCode;
        this.executeOut = executeOut;
    }

}