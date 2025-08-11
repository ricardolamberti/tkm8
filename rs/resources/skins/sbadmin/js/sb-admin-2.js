/*!
 * Start Bootstrap - SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
 * Copyright 2013-2016 Start Bootstrap
 * Licensed under MIT (https://github.com/BlackrockDigital/startbootstrap/blob/gh-pages/LICENSE)
 */
$(function() {
    $('#side-menu').metisMenu();
});

//Loads the correct sidebar on window load,
//collapses the sidebar on window resize.
// Sets the min-height of #page-wrapper to window size
$(function() {
    $(window).bind("load resize", function() {
        var topOffset = 50;
        var width = (this.window.innerWidth > 0) ? this.window.innerWidth : this.screen.width;
        if (width < 768) {
            $('div.navbar-collapse').addClass('collapse');
    		$("#page-wrapper").css("margin-left","0px");
            topOffset = 100; // 2-row-menu
        } else {
            $('div.navbar-collapse').removeClass('collapse');
        }

        var height = ((this.window.innerHeight > 0) ? this.window.innerHeight : this.screen.height) - 1;
        height = height - topOffset;
        if (height < 1) height = 1;
        if (height > topOffset) {
            $("#page-wrapper").css("min-height", (height) + "px");
        }
        
    	if ( $('div.navbar-collapse').hasClass('collapse') )
    		$("#page-wrapper").css("margin-left","0px");
    	else if ($('.sidebar-nav').hasClass("collapsed"))
    		$("#page-wrapper").css("margin-left","60px");
    	else
    		$("#page-wrapper").css("margin-left","250px");

    });

    var url = window.location;
    // var element = $('ul.nav a').filter(function() {
    //     return this.href == url;
    // }).addClass('active').parent().parent().addClass('in').parent();
    var element = $('ul.nav a').filter(function() {
        return this.href == url;
    }).addClass('active').parent();

    while (true) {
        if (element.is('li')) {
            element = element.parent().addClass('in').parent();
        } else {
            break;
        }
    }


	$('.btn-expand-collapse').click(function(e) {
	
			$('#side-menu_sidebar').toggleClass('collapsed');
	
			if ($('div.navbar-collapse').hasClass('collapse')) {
				$("#page-wrapper").css("margin-left", "0px");
			} else if ($('#side-menu_sidebar').hasClass("collapsed")) {
				$("#page-wrapper").css("margin-left", "60px");
				
				$('.btn-expand-collapse i.glyphicon-menu-left').each(function() {
					$(this).removeClass('glyphicon-menu-left');
					$(this).addClass('glyphicon-menu-right');
				});
			} else {
				$("#page-wrapper").css("margin-left", "250px");
				$('.btn-expand-collapse i.glyphicon-menu-right').each(function() {
					$(this).removeClass('glyphicon-menu-right');
					$(this).addClass('glyphicon-menu-left');
				});
			}

		});
	
		$('.nav_group').mouseover(function(e) {
	
			if ($('div.navbar-collapse').hasClass('collapse'))
				return;
	
			if (!$('.sidebar-nav').hasClass("collapsed"))
				return;
	
			$('#side-menu_sidebar').toggleClass('collapsed');
			$('#side-menu_sidebar').toggleClass('over');
	
			$("#page-wrapper").css("margin-left", "250px");
	
		});
	
		$('.nav_group').mouseout(function(e) {
	
			if ($('div.navbar-collapse').hasClass('collapse'))
				return;
	
			if (!$('.sidebar-nav').hasClass("over"))
				return;
	
			$('#side-menu_sidebar').toggleClass('collapsed');
			$('#side-menu_sidebar').toggleClass('over');
	
			$("#page-wrapper").css("margin-left", "60px");
	
		});

});
