package ua.training.controller.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.SkipPageException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Create exception alert where user can see an information about exception was occurred
 */
public class ExceptionTag extends SimpleTagSupport {

    private String exception;

    public void setException(String exception) {
        this.exception = exception;
    }

    @Override
    public void doTag() throws JspException, IOException {
        PageContext pageContext = (PageContext) getJspContext();
        JspWriter out = pageContext.getOut();

        try {
            StringBuilder sb = new StringBuilder();
            if (exception != null && !exception.isEmpty()) {
                createAlert(sb);
                out.println(sb.toString());
            }
        } catch (Exception e) {
            throw new SkipPageException("Error while creating alert for exception");
        }
    }

    private void createAlert(StringBuilder sb) {
        sb.append("<div class=\"alert alert-danger alert-dismissible center-block\" style=\"width: 90%;\">\n")
                .append("<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n")
                .append("<span aria-hidden=\"true\">&times;</span>\n")
                .append("</button>")
                .append(exception)
                .append("</div>\n");
    }
}
