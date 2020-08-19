<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<ul class="nav nav-list">
					<%--<li class="">
						<a href="main/index">
							<i class="menu-icon fa fa-tachometer"></i>
							<span class="menu-text">后台首页</span>
						</a>
						<b class="arrow"></b>
					</li>--%>
					<li class=""  id="A">
						<a style="cursor:pointer;" target="mainFrame" class="dropdown-toggle">
							<i class="menu-icon fa fa-cogs blue"></i>
							<span class="menu-text">
								前端代码生成
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li class="" id="bb">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('bb','A','生成记录','/createCode/list?flag=front')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
										生成记录
								</a>
							</li>
							<li class="" id="cc">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('cc','A','代码生成','/recreateCode/list?flag=front')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
										代码生成
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="B">
						<a style="cursor:pointer;" target="mainFrame" class="dropdown-toggle">
							<i class="menu-icon fa fa-cogs green"></i>
							<span class="menu-text">
								后端代码生成
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li class="" id="g">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('g','B','生成记录','/createCode/list?flag=backend')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
										生成记录
								</a>
							</li>
							<li class="" id="h">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('h','B','代码生成','/recreateCode/list?flag=backend')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
										代码生成
								</a>
							</li>
						</ul>
					</li>
					<li class=""  id="C">
						<a style="cursor:pointer;" target="mainFrame" class="dropdown-toggle">
							<i class="menu-icon fa fa-cogs brown"></i>
							<span class="menu-text">
								Full Code
							</span>
							<b class="arrow fa fa-angle-down"></b>
						</a>
						<b class="arrow"></b>
						<ul class="submenu">
							<li class="" id="m">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('m','C','生成记录','/createCode/list?flag=full')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
									生成记录
								</a>
							</li>
							<li class="" id="n">
								<a style="cursor:pointer;" target="mainFrame" onclick="siMenu('n','C','代码生成','/recreateCode/list?flag=full')" class="dropdown-toggle">
									<i class="menu-icon fa fa-leaf black"></i>
									代码生成
								</a>
							</li>
						</ul>
					</li>
				</ul><!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>