<%-- 
    Document   : eventos
    Created on : 26/09/2012, 04:50:53 PM
    Author     : sebasariz
--%>

<%@page pageEncoding="UTF-8" contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>


<div class="page-header font-header no-breadcrumb">
    Chat
    <div class="header-toolbar font-main">
        <div class="btn-toolbar font-12" role="toolbar">
            <div class="btn-group" role="group">
                <!--<button type="button" class="btn btn-main">Create Group</button>-->
            </div>
        </div>
    </div>
</div>
<div class="app-wrapper chat-wrapper has-page-header app-40-60">
    <div class="app-col chat-list bg-white">
        <div class="app-col-header">
            <div class="input-link-in">
                <input class="form-control input-rounded input-sm" placeholder="Friend's Name">
                <a href="#" class="link-in"><i class="icon-search"></i></a>
            </div>
        </div>

        <div class="app-scrollable-content">
            <ul class="chat-friend-list list-unstyled" id="actividades">
                 
            </ul>
        </div><!-- /.app-scrollable-content -->
    </div>
    <div class="app-col app-content-col chat-content bg-white">
        <div class="loading-wrap">
            <div class="loading-dots">
                <div class="dot1"></div>
                <div class="dot2"></div>
            </div>
        </div>
        <div class="app-col-header">
            <div class="btn-toolbar" role="toolbar">
                <div class="btn-group" role="group" aria-label="First group">
                    <button type="button" class="btn btn-default btn-icon visible-xs chat-back"><i class="icon-circle-left m-d-1"></i></button>
                    <button type="button" class="btn btn-link no-p-l p-l-10-xs">
                        <i class="fa fa-circle text-success"></i> <label id="act"> </label>
                    </button>
                </div> 
            </div>
        </div><!-- /.app-col-header -->

        <div class="app-scrollable-content" id="comentarios">
            <div class="chat-items font-12">
                        
            </div><!-- /.chat-items -->
        </div><!-- /.app-scrollable-content -->
        <div class="chat-reply bg-white">
            <div class="input-group"> 
                <input type="text" class="form-control input-sm" id="comentarioString">
                <span class="input-group-btn">
                    <button class="btn btn-default btn-sm" onclick="saveComment()" type="button">Send</button>
                </span>
            </div>
        </div><!-- /.chat-reply -->
    </div>
</div><!-- /.task-wrapper -->

<script src="js-in/actividades_tablero.js"></script>
<script>
    $(function () {
        init(<%=request.getAttribute("grid")%>);
    });
</script>
<script>

</script>

