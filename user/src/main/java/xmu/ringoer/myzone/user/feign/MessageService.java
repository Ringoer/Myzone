package xmu.ringoer.myzone.user.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import xmu.ringoer.myzone.user.domain.Message;

/**
 * @author Ringoer
 */
@FeignClient(name = "MYZONE-MESSAGE")
public interface MessageService {

    /**
     * 调用 MessageService 服务发送一条消息
     * @param userId 发件人id
     * @param data 包含消息和目标id集合
     * @return 响应
     */
    @PostMapping("/")
    Object postMessage(@RequestHeader Integer userId, @RequestBody JSONObject data);
}
