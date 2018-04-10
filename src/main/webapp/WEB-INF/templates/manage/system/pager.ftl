<style type="text/css">
.pageLink {
	border: 1px solid #dddddd;
	padding: 4px 12px;
	text-decoration: none;
}

.selectPageLink {
	border: 1px solid #0088cc;
	padding: 4px 12px;
	color: #0088cc;
	background-color: #dddddd;
	text-decoration: none;
}
</style>

        <!-- 分页标签 -->
        <div style="text-align: right; border: 0;padding: 4px 12px;" class="pageDiv">
            总共：${pager.total}条,共:${pager.pagerSize}页

	<#--
		自定义指令page的参数说明，如下
		pagerUrl       用于指定链接所跳转的URL
		totalPages    用于指定总页数
		currPage      用于指定当前显示第几页，默认显示第一页
		pageNumber    显示的页码
		class         用于指定<a/>的class属性
    -->
	<@page totalPages=pager.pagerSize pagerUrl=pager.pagerUrl currPage=pageNo pageNumber=6 class="pageLink"></@page>
	<#macro page totalPages pagerUrl currPage pageNumber class>
	<#if currPage ==1 >
        <span class="pageLink">上一页</span>
	<#else>
        <a href="${pagerUrl+"?pager.offset="}${(currPage-2)*pager.pageSize}" class="${class}">上一页</a>
	</#if>
    <#if totalPages lte pageNumber >
		<#local begin=1 />
		<#local end=totalPages/>
	<#else>
		<#local begin=currPage-2 />
		<#local end=currPage+3/>
		<#if begin lt 1>
			<#local begin=1 />
			<#local end=pageNumber/>
		</#if>

		<#if end gt totalPages>
			<#local begin=totalPages-5 />
			<#local end=totalPages/>
		</#if>
    </#if>
<@showPage begin=begin end=end pn=currPage url=pagerUrl tPages=totalPages linkstyle=class></@showPage>
</#macro>
		<#macro showPage begin end pn url tPages linkstyle>
		<#list begin..end as i>
			<#if i ==pn >
                <span class="selectPageLink">${i}</span>
			<#else>
                <a href="${url+"?pager.offset="}${(i-1)*pager.pageSize}" class="${linkstyle}" >${i}</a>
			</#if>
		</#list>
		<#if end lt tPages>
			<span class="spanApostrophe">...</span>
		</#if>
		<#if pn==tPages>
			<span class="${linkstyle}">下一页</span>
		<#else>
			<a href="${url+"?pager.offset="}${pn*pager.pageSize}" class="${linkstyle}">下一页</a>
		</#if>
	</#macro>
	</div>


