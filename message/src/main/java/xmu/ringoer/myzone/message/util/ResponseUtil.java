package xmu.ringoer.myzone.message.util;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Ringoer
 * 响应操作结果
 * <pre>
 *  {
 *      errno： 错误码，
 *      errmsg：错误消息，
 *      data：  响应数据
 *  }
 * </pre>
 *
 * <p>
 * 错误码：
 * <ul>
 * <li> 0，成功；
 * <li> 4xx，前端错误，说明前端开发者需要重新了解后端接口使用规范：
 * <ul>
 * <li> 401，参数错误，即前端没有传递后端需要的参数；
 * <li> 402，参数值错误，即前端传递的参数值不符合后端接收范围。
 * </ul>
 * <li> 5xx，后端错误，除501外，说明后端开发者应该继续优化代码，尽量避免返回后端错误码：
 * <ul>
 * <li> 501，验证失败，即后端要求用户登录；
 * <li> 502，系统内部错误，即没有合适命名的后端内部错误；
 * <li> 503，业务不支持，即后端虽然定义了接口，但是还没有实现功能；
 * <li> 504，更新数据失效，即后端采用了乐观锁更新，而并发更新时存在数据更新失效；
 * <li> 505，更新数据失败，即后端数据库更新失败（正常情况应该更新成功）。
 * </ul>
 * <li> 6xx，小商城后端业务错误码，
 * 具体见litemall-admin-api模块的AdminResponseCode。
 * <li> 7xx，管理后台后端业务错误码，
 * 具体见litemall-wx-api模块的WxResponseCode。
 * </ul>
 */
public class ResponseUtil {

    public final static Integer HTTP_SUCCESS = 200;

    public final static Integer SERVER_ERROR = 500;

    public final static Integer BAD_ARGUMENT = 580;

    public final static Integer FAIL_OPERATION = 676;

    public final static Integer SUCCESS = 0;

    public static JSONObject ok() {
        JSONObject object = new JSONObject();
        object.put("errno", SUCCESS);
        object.put("errmsg", "成功");
        return object;
    }

    public static JSONObject ok(Object data) {
        JSONObject object = new JSONObject();
        object.put("errno", SUCCESS);
        object.put("errmsg", "成功");
        object.put("data", data);
        return object;
    }

    public static JSONObject fail() {
        JSONObject object = new JSONObject();
        object.put("errno", -1);
        object.put("errmsg", "错误");
        return object;
    }

    public static JSONObject fail(int errno, String errmsg) {
        JSONObject object = new JSONObject();
        object.put("errno", errno);
        object.put("errmsg", errmsg);
        return object;
    }

    public static JSONObject badArgument() {
        return fail(580, "参数错误");
    }

    public static JSONObject badArgumentValue() {
        return fail(402, "参数值不对");
    }

    public static JSONObject serious() {
        return fail(502, "系统内部错误");
    }

    public static JSONObject unsupport() {
        return fail(503, "业务不支持");
    }

    public static JSONObject updatedDateExpired() {
        return fail(504, "更新数据已经失效");
    }

    public static JSONObject updatedDataFailed() {
        return fail(505, "更新数据失败");
    }

    public static JSONObject wrongPage() {
        return fail(506, "页码越界");
    }

    public static JSONObject wrongCode() {
        return fail(667, "验证码错误");
    }

    public static JSONObject notfound() {
        return fail(404, "找不到页面");
    }

    public static JSONObject failLog() {
        return fail(FAIL_OPERATION, "日志服务未开启");
    }
}
