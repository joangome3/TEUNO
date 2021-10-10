<%--
zksandbox.css.dsp

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Thu Jul 1 11:27:21     2009, Created by kindalu
		Thu Jul 29 16:45:36    2010, Modified by simon
}}IS_NOTE

Copyright (C) 2008 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
--%><%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<%@ taglib uri="http://www.zkoss.org/dsp/zk/core" prefix="z" %>
<c:include page="~./zul/css/ext.css.dsp"/>
${z:setCSSCacheControl()}

html {overflow:auto;}
img { -ms-interpolation-mode:bicubic }
body {
	padding: 0 !important;
}
h4 {
	margin: 0;
	padding: 10px 0;
}
P {
	margin: 0;
	padding: 5px 0;
}
ul {
	margin-top: 5px;
	margin-bottom: 5px;
}
a, a:visited {
	color:#008bb6;
}

.demo-search-inp {
    padding: 2px 0 1px 18px;
	background: white url(${c:encodeURL(zk.ie == 6 ? '/pictures/search.gif' : '/pictures/search.png')}) no-repeat scroll 0 0;
}

</c:if>