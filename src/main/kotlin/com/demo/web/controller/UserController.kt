package com.demo.web.controller

import com.demo.base.UserType
import com.demo.entity.User
import com.demo.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.ModelMap
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*

import javax.validation.Valid
import java.util.Arrays

/**
 * User Controller
 *
 * @version Revision History
 * <pre>
 * Author   Version     Date            Changes
 * pankplee  1.0         9/20/2017         Created
</pre> *
 * @since 1.0
 */
@Controller
@RequestMapping("/user")
class UserController {

    private lateinit var userService: UserService

    @ModelAttribute("allUserTypes")
    fun populateTypes(): List<UserType> {
        return Arrays.asList(*UserType.values())
    }

    @Autowired
    fun setUserService(userService: UserService) {
        this.userService = userService
    }

    @RequestMapping(value = "/create/{id:\\d*}", method = arrayOf(RequestMethod.GET))
    @ResponseBody
    fun create(@PathVariable id: Long): String {
        val user = userService.create("Lee.$id@demo.com", "demo", UserType.USER)
        return user.toString()
    }

    @RequestMapping(value = *arrayOf("", "/list"), method = arrayOf(RequestMethod.GET))
    fun show(model: Model): String {
        val users = userService.findAll()
        model.addAttribute("users", users)
        return "user/list"
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.GET))
    fun createForm(model: Model): String {
        model.addAttribute("user", User())
        return "user/create"
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    fun postForm(@Valid user: User, bindingResult: BindingResult, model: ModelMap): String {
        if (bindingResult.hasErrors()) {
            return "user/create"
        }
        userService.create(user)
        model.clear()
        return "redirect:/user"
    }

    @RequestMapping(value = "/delete/{id:\\d*}")
    fun delete(@PathVariable id: Long, model: ModelMap): String {
        userService.remove(id)
        model.clear()
        return "redirect:/user"
    }
}
