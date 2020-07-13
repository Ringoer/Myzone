package xmu.ringoer.myzone.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import xmu.ringoer.myzone.user.service.UserService;
import xmu.ringoer.myzone.user.util.EmailUtil;

@SpringBootTest
@Transactional
class UserApplicationTests {

	@Test
	void contextLoads() {
		String code = "2333";
		String text = "您正在获得来自 Myzone 的验证码。<br><br>您的验证码是：<br><br>" + code + "<br><br>您的验证码有效期为 5 分钟。<br><br>若不是您本人申请，请忽略此邮件。";
		EmailUtil.sendMail("ringoer@qq.com", text, "来自 Myzone 的验证码");
	}

}
