package ua.training.controller.tags;

import ua.training.model.entities.User;
import ua.training.util.constant.Messages;
import ua.training.util.locale.LocalizeMessage;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Create a table with information about user in user account page
 */
public class UserAccountTag extends SimpleTagSupport {

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder sb = new StringBuilder();

            String[] information = {user.getSurname(), user.getName(), user.getLogin(), user.getPhone(), String.valueOf(user.getMoney())};
            String[] namesOfInfo = {Messages.SURNAME, Messages.NAME, Messages.LOGIN, Messages.PHONE, Messages.BALANCE};

            sb.append("<table>");
            for (int i = 0; i < information.length; i++) {
                sb.append("<tr>")
                        .append("<td>")
                        .append("<h4 class=\"text-left\"><b>")
                        .append(LocalizeMessage.getMessage(namesOfInfo[i]))
                        .append("</b></h4>")
                        .append("</td>")
                        .append("<td>")
                        .append("<h4 class=\"text-left\">")
                        .append("&nbsp;&nbsp;")
                        .append(information[i])
                        .append("</h4>")
                        .append("</td>")
                        .append("</tr>");
            }
            sb.append("</table>");
            out.println(sb.toString());
        } catch (Exception e) {
            throw new SkipPageException("Error while creating table with user's information");
        }
    }
}