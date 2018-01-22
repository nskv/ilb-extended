package main.java.karchevsky.apache.ilb.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class LocalCacheController {

	@ApiOperation(value = "test controller")
	@RequestMapping(path="test controller", value="test controller", method=RequestMethod.GET)
	@ResponseBody
	public String testController() {
		return "controller ready";
	}
}
