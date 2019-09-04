package response;



/**
 * @description:
 * @author: gaoweiwei_v
 * @time: 2019/8/1 2:46 PM
 */
        import io.swagger.annotations.ApiModel;
        import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "返回结果")
public class RestResponse {
    @ApiModelProperty("状态码 0:成功")
    private int code;
    @ApiModelProperty("消息")
    private String message;
    @ApiModelProperty("返回数据")
    private Object data;

    public RestResponse(){

    }



    public void setData(Object data){
        this.data = data;
    }

    public Object getData(){
        return data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
