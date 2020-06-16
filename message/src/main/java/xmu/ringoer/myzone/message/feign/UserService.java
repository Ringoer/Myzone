package xmu.ringoer.myzone.message.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author Ringoer
 */
@FeignClient(name = "MYZONE-USER")
public interface UserService {

    /**
     * 调用 UserService 服务检查用户id是否存在
     * @param userId 用户id
     * @return 响应
     */
    @GetMapping("/info")
    Object getInfo(@RequestHeader Integer userId);
}
