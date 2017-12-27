package stam

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody}

@Controller
class RootController {

  @RequestMapping(Array("/"))
  @ResponseBody
  def handleRootRequest(): String = "redirect:/hotels"
}