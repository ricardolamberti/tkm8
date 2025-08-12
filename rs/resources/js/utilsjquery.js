initialize = function (json) {
	AAInit();
	json.actionMap.forEach(function (value, index) {
		if (value.rows) {
			AAAdd(value.action,value.win,value.rows,value.multiple=="true");
		} else {
			AAANodd(value.action,value.win,value.norows,value.multiple=="true");
		}
	}); 
	AAFin(json.provider+'_table',json.actionbarname,json.provider,json.zobject,json.multiselect=="true",json.islineselect=="true",json.moreselection,true,null);
	setupZTables();
}

$.fn.dataTable.pipeline = function ( opts ) {
    // Configuration options
    var conf = $.extend( {
        pages: 5,     // number of pages to cache
        url: '',      // script url
        data: null,   // function or object with parameters to send to the server
                      // matching how `ajax.data` works in DataTables
        method: 'POST' // Ajax HTTP method
    }, opts );
 
    // Private variables for storing the cache
    var cacheLower = -1;
    var cacheUpper = null;
    var cacheLastRequest = null;
    var cacheLastJson = null;
  
    return function ( request, drawCallback, settings ) {
        var ajax          = false;
        var requestStart  = request.start;
        var drawStart     = request.start;
        var requestLength = request.length;
        var requestEnd    = requestStart + requestLength;
        
        if ($(this).closest('div[class^="tab-pane"]').hasClass('tab-pane')) {
            if (!$(this).closest('div[class^="tab-pane"]').hasClass('active')) {

                  return;
            }
        	
        }
         
        if ( settings.clearCache ) {
            // API requested that the cache be cleared
            ajax = true;
            settings.clearCache = false;
        }
        else if ( cacheLower < 0 || requestStart < cacheLower || requestEnd > cacheUpper ) {
            // outside cached data - need to make a request
            ajax = true;
        }
        else if ( JSON.stringify( request.order )   !== JSON.stringify( cacheLastRequest.order ) ||
                  JSON.stringify( request.columns ) !== JSON.stringify( cacheLastRequest.columns ) ||
                  JSON.stringify( request.search )  !== JSON.stringify( cacheLastRequest.search )
        ) {
            // properties changed (ordering, columns, searching)
            ajax = true;
        }
         
        // Store the request for checking next time around
        cacheLastRequest = $.extend( true, {}, request );
 
        if ( ajax ) {
            // Need data from the server
            if ( requestStart < cacheLower ) {
                requestStart = requestStart - (requestLength*(conf.pages-1));
 
                if ( requestStart < 0 ) {
                    requestStart = 0;
                }
            }
             
            cacheLower = requestStart;
            cacheUpper = requestStart + (requestLength * conf.pages);
 
            request.start = requestStart;
            request.length = requestLength*conf.pages;
 
            // Provide the same `data` options as DataTables.
            if ( $.isFunction ( conf.data ) ) {
                // As a function it is executed with the data object as an arg
                // for manipulation. If an object is returned, it is used as the
                // data object to submit
                var d = conf.data( request );
                if ( d ) {
                    $.extend( request, d );
                }
            }
            else if ( $.isPlainObject( conf.data ) ) {
                // As an object, the data given extends the default
                $.extend( request, conf.data );
            }
 
            settings.jqXHR = $.ajax( {
                "type":     conf.method,
                "url":      conf.url,
                "data":     request,
                "dataType": "json",
                "cache":    false,
                "success":  function ( json ) {
                    cacheLastJson = $.extend(true, {}, json);
 
                    if ( cacheLower != drawStart ) {
                        json.data.splice( 0, drawStart-cacheLower );
                    }
                    if ( requestLength >= -1 ) {
                        json.data.splice( requestLength, json.data.length );
                    }
                     
                    initialize(json);
//					AAInit();
//					json.actionMap.forEach(function (value, index) {
//						if (value.rows) {
//							AAAdd(value.action,value.win,value.rows,value.multiple=="true");
//						} else {
//							AAANodd(value.action,value.win,value.norows,value.multiple=="true");
//						}
//					}); 
//					AAFin(json.provider+'_table',json.actionbarname,json.provider,json.zobject,json.multiselect=="true",json.islineselect=="true",json.moreselection,true,null);
//					setupZTables();

					drawCallback( json );
					
					if (json.foot) {
				    	var api = $('#'+json.provider+'_table').dataTable().api();
				    	
				    	var i=0;
						json.foot.forEach(function (tot, index) {
								if (tot.col_name!='') {
									//$(api.column(tot.col_name+":name").footer()).html(tot.value); dont work
									if (api.column(i).footer()!=null)
										$(api.column(i++).footer()).html(tot.value);
								}	
							
					    });
	                }

                },
            	"error":  function ( xhr, ajaxOptions, thrownError) {
                    alert(xhr.status);
                    alert(thrownError);
                  }
            } );
        }
        else {
            json = $.extend( true, {}, cacheLastJson );
            json.draw = request.draw; // Update the echo for each response
            json.data.splice( 0, requestStart-cacheLower );
            json.data.splice( requestLength, json.data.length );
 
            drawCallback(json);

			
			if (json.foot) {
		    	var api = $('#'+json.provider+'_table').dataTable().api();
		    	
		    	var i=0;
				json.foot.forEach(function (tot, index) {
						i++;
						if (tot.col_name!='') {
							$(api.column(i).footer()).html(tot.value);
						}	
					
			    });
            }


        }
    }
};
 
$.fn.dataTable.Api.register( 'clearPipeline()', function () {
    return this.iterator( 'table', function ( settings ) {
        settings.clearCache = true;
    } );
} );



+function ($) {
	'use strict';

	// TOGGLE PUBLIC CLASS DEFINITION
	// ==============================

	var Toggle = function (element, options) {
		this.$element  = $(element)
		this.options   = $.extend({}, this.defaults(), options)
		this.render()
	}

	Toggle.VERSION  = '2.2.0'

	Toggle.DEFAULTS = {
		on: 'On',
		off: 'Off',
		onstyle: 'primary',
		offstyle: 'default',
		size: 'normal',
		style: '',
		width: null,
		height: null
	}

	Toggle.prototype.defaults = function() {
		return {
			on: this.$element.attr('data-on') || Toggle.DEFAULTS.on,
			off: this.$element.attr('data-off') || Toggle.DEFAULTS.off,
			onstyle: this.$element.attr('data-onstyle') || Toggle.DEFAULTS.onstyle,
			offstyle: this.$element.attr('data-offstyle') || Toggle.DEFAULTS.offstyle,
			size: this.$element.attr('data-size') || Toggle.DEFAULTS.size,
			style: this.$element.attr('data-style') || Toggle.DEFAULTS.style,
			width: this.$element.attr('data-width') || Toggle.DEFAULTS.width,
			height: this.$element.attr('data-height') || Toggle.DEFAULTS.height
		}
	}

	Toggle.prototype.render = function () {
		this._onstyle = 'btn-' + this.options.onstyle
		this._offstyle = 'btn-' + this.options.offstyle
		var size = this.options.size === 'large' ? 'btn-lg'
			: this.options.size === 'small' ? 'btn-sm'
			: this.options.size === 'mini' ? 'btn-xs'
			: ''
		if (typeof this.$element === "undefined") return;
		var $toggleOn = $('<label class="btn">').html(this.options.on)
			.addClass(this._onstyle + ' ' + size)
		var $toggleOff = $('<label class="btn">').html(this.options.off)
			.addClass(this._offstyle + ' ' + size + ' active')
		var $toggleHandle = $('<span class="toggle-handle btn btn-default">')
			.addClass(size)
		var $toggleGroup = $('<div class="toggle-group">')
			.append($toggleOn, $toggleOff, $toggleHandle)
		$toggleOn.addClass('toggle-on')
		$toggleOff.addClass('toggle-off')
		var $toggle = $('<div class="toggle btn" data-toggle="toggle" id="'+this.$element[0].id+'_div">')
			.addClass( this.$element.prop('checked') ? this._onstyle : this._offstyle+' off' )
			.addClass(size).addClass(this.options.style)

		this.$element.wrap($toggle)
		$.extend(this, {
			$toggle: this.$element.parent(),
			$toggleOn: $toggleOn,
			$toggleOff: $toggleOff,
			$toggleGroup: $toggleGroup
		})
		this.$toggle.append($toggleGroup)

		var width = this.options.width || Math.max($toggleOn.outerWidth(), $toggleOff.outerWidth())+($toggleHandle.outerWidth()/2)
		var height = this.options.height || Math.max($toggleOn.outerHeight(), $toggleOff.outerHeight())

		this.$toggle.css({ width: width, height: height })
		if (this.options.height) {
			$toggleOn.css('line-height', $toggleOn.height() + 'px')
			$toggleOff.css('line-height', $toggleOff.height() + 'px')
		}
		this.update(true)
		this.trigger(true)
	}

	Toggle.prototype.toggle = function () {
		if (this.$element.prop('checked')) this.off()
		else this.on()
	}

	Toggle.prototype.on = function (silent) {
		if (this.$element.prop('disabled')) return false
		this.$toggle.removeClass(this._offstyle + ' off').addClass(this._onstyle)
		this.$element.prop('checked', true)
		if (!silent) this.trigger()
	}

	Toggle.prototype.off = function (silent) {
		if (this.$element.prop('disabled')) return false
		this.$toggle.removeClass(this._onstyle).addClass(this._offstyle + ' off')
		this.$element.prop('checked', false)
		if (!silent) this.trigger()
	}

	Toggle.prototype.enable = function () {
		this.$toggle.removeAttr('disabled')
		this.$element.prop('disabled', false)
	}

	Toggle.prototype.disable = function () {
		this.$toggle.attr('disabled', 'disabled')
		this.$element.prop('disabled', true)
	}

	Toggle.prototype.update = function (silent) {
		if (this.$element.prop('disabled')) this.disable()
		else this.enable()
		if (this.$element.prop('checked')) this.on(silent)
		else this.off(silent)
	}

	Toggle.prototype.trigger = function (silent) {
		this.$element.off('change.bs.toggle')
		if (!silent) this.$element.change()
		this.$element.on('change.bs.toggle', $.proxy(function() {
			this.update()
		}, this))
	}

	Toggle.prototype.destroy = function() {
		this.$element.off('change.bs.toggle')
		this.$toggleGroup.remove()
		this.$element.removeData('bs.toggle')
		this.$element.unwrap()
	}

	// TOGGLE PLUGIN DEFINITION
	// ========================

	function PluginToogle(option) {
		//return this.each(function () {
			var $this   = $(this)
			var data    = $this.data('bs.toggle')
			var options = typeof option == 'object' && option

			if (!data) $this.data('bs.toggle', (data = new Toggle(this, options)))
			if (typeof option == 'string' && data[option]) data[option]()
		//})
	}

	var old = $.fn.bootstrapToggle

	$.fn.bootstrapToggle             = PluginToogle
	$.fn.bootstrapToggle.Constructor = Toggle 

	// TOGGLE NO CONFLICT
	// ==================

	$.fn.toggle.noConflict = function () {
		$.fn.bootstrapToggle = old
		return this
	}

	// TOGGLE DATA-API
	// ===============

		$(function() {
//		$('input[type=checkbox][data-toggle^=toggle]').bootstrapToggle()
	})

	$(document).on('click.bs.toggle', 'div[data-toggle^=toggle]', function(e) {
		var $checkbox = $(this).find('input[type=checkbox]');
		onFocusObject(this);
		if (!($checkbox.hasClass('refreshform')))
			$checkbox.bootstrapToggle('toggle');
		$checkbox.click();
		e.preventDefault();
	})

}(jQuery);

function removeAffixObject(name) {
	$(document).off("scroll");
}
function affixObject(name) {
	$(document).off("scroll");
	$(document).on("scroll", {
		object: name, oldtop: $("#"+name).offset().top
	},onScroll);
	$("#"+name).parent().css({ 'min-height':$("#"+name).height()+$("#"+name).offset().left});
}

function onScroll(e){
	var name= e.data.object;
	
	if ($("#"+name).offset()==="undefined") return;
    if($("#"+name).hasClass("affix")){
        if($(document).scrollTop() <= e.data.oldtop) {
            $("#"+name).removeClass("affix");
            $("#"+name).css({ top:'' })
            $("#"+name).css({ width:'' })
            e.data.oldtop= $("#"+name).offset().top;
        }

      
        return;
    }

    if($(document).height()-50 <= $("#"+name).offset().top+$("#"+name).height()){
       return;
    }
//    if($(document).scrollTop() <= e.data.oldtop)
//        return;
    if($("#"+name).hasClass("affix"))
    	return;


	var posObj=  $("#"+name).offset().top+$("#"+name).height();
	var posView= $(document).scrollTop()+window.innerHeight;
	var posTop= $("#"+name).height()-window.innerHeight;

	
    if (posObj<posView) {
    		e.data.oldtop=$(document).scrollTop();
    		var realAncho = $("#"+name).width();
        $("#"+name).addClass("affix");
        $("#"+name).css({ top: '-'+posTop+'px' })
        $("#"+name).css({ width: realAncho+'px' })
    }
//    if($(document).scrollTop() > e.data.oldtop){
//        $("#"+name).addClass("affix");
 //   }
}

function initializeIdiomaSelect2 () {
	if (jQuery && jQuery.fn && jQuery.fn.select2 && jQuery.fn.select2.amd)
		var e = jQuery.fn.select2.amd;
	return e.define("select2/i18n/es", [], function () {
		return {
			errorLoading: function () {
				return global_errorLoading
			},
			inputTooLong: function (e) {
				var t = e.input.length - e.maximum,
				n = global_inputTooLong1 + t ;
				return t == 1 ? n += global_inputTooLong2 : n += global_inputTooLong3,
				n
			},
			inputTooShort: function (e) {
				var t = e.minimum - e.input.length,
				n = global_inputTooShort1 + t ;
				return t == 1 ? n += global_inputTooShort2 : n += global_inputTooShort3,
				n
			},
			loadingMore: function () {
				return global_loadingMore
			},
			maximumSelected: function (e) {
				var t = global_maximumSelected1 + e.maximum ;
				t+=e.maximum == 1 ? global_maximumSelected2 : global_maximumSelected3;
				return t
			},
			noResults: function () {
				return global_noResults
			},
			searching: function () {
				return  global_searching
			}
		}
	}), {
		define: e.define,
		require: e.require
	}
};
function format(state) {
	var rid = state.real_id;
	if (!state.real_id && state.element && state.element.dataset  && state.element.dataset.real_id) rid =state.element.dataset.real_id;
    if (!rid)  
    	return state.text; 
    $(state.element).attr('data-real_id',rid);
    return  $("<SPAN><B>"+rid+"</B> "+state.text+"</SPAN>");
}
function inicializeSelect2(name,placeholder,max,min,withkey,multiple,showLupa) {
	initializeIdiomaSelect2();
	anchors.options.placement = 'left';
	anchors.add('.container h1, .container h2, .container h3, .container h4, .container h5');
    var allowClear = (typeof showLupa === 'undefined') ? false:!showLupa;

	$.fn.select2.defaults.set( "theme", "bootstrap" );

	if (withkey==1) {
		$( "#"+name).select2( {
			width: null,
			allowClear: allowClear,
			placeholder: placeholder,
			language: "es",
	      templateResult: function (result) {
	        return format(result);
	      },
	      templateSelection: function (selection) {
	        return format(selection);
	      },
	      
			maximumSelectionLength: max,
			minimumInputLength: min,
	
			containerCssClass: ':all:',
			ajax: {
				url: "do-comboResponsiveFormLovAction",
				dataType: 'json',
				delay: 250,
				data: function (params) {
					return responsiveFillDataListJSON( name,params.term);
				},
				cache: true
			}
		});
	} else {
		$( "#"+name).select2( {
			width: null,
			allowClear: allowClear,
			placeholder: placeholder,
			language: "es",
			maximumSelectionLength: max,
			minimumInputLength: min,
	
			containerCssClass: ':all:',
			ajax: {
				url: "do-comboResponsiveFormLovAction",
				dataType: 'json',
				delay: 250,
				data: function (params) {
					return responsiveFillDataListJSON( name,params.term);
				},
				cache: true
			}
		});
	}	
	$( "#"+name).off("select2:unselect")
	$( "#"+name).off("select2:select")
	$( "#"+name).off("select2:open")

	$( "#"+name).on( "select2:open", function() {
		if ( $( this ).parents( "[class*='has-']" ).length ) {
			var classNames = $( this ).parents( "[class*='has-']" )[ 0 ].className.split( /\s+/ );

			for ( var i = 0; i < classNames.length; ++i ) {
				if ( classNames[ i ].match( "has-" ) ) {
					$( "body > .select2-container" ).addClass( classNames[ i ] );
				}
			}
		}
	});
	if (multiple) {
	  	$( "#"+name).on("select2:unselect", function(e) {
	  		$( "#"+name).select2("close");
	  		refreshResponsiveFormLov(name);
	  	});
	}
	$( "#"+name).on("select2:select", function(e) {
  		$(  "#"+name ).select2("close");
  		refreshResponsiveFormLov(name);
  	});	
  	$(  "#"+name ).parent().find(".select2-selection--single").on("keydown", function(e) {
  		var x = e.which || e.keyCode; 
		if (x>=37 && x<=40 ) {
			var out= gaOnKey(event);
			return true;
		}
  		if (x!=32 &&  e.key.match(/^[\d\w]$/i)) {
//  			var event = $.Event("keydown", { which: 32,keyCode: 32 });
//	  		$(this).trigger( event );
//				$(".select2-search__field").val(String.fromCharCode(e.which));
//				$( "#"+name ).val(String.fromCharCode(e.which));
//				$( "#"+name ).trigger('change.select2');
			  var $select = $(  "#"+name );
			  select2_search($select, String.fromCharCode(e.which));

  		} 
  		
  	});	


}

function select2_search ($el, term) {
  $el.select2('open');
  
  // Get the search box within the dropdown or the selection
  // Dropdown = single, Selection = multiple
  var $search = $el.data('select2').dropdown.$search || $el.data('select2').selection.$search;
  // This is undocumented and may change in the future
  
  $search.val(term);
  $search.trigger('keyup');
}

function clearSelect2(name,refreshform) {
	$("#"+name).empty();
	$("#"+name).trigger("change");
	if (refreshform)
		refreshResponsiveFormLov(name);
}
function inicializeSwaplist(name) {
	if (!(jQuery && jQuery.fn)) return;
	var nameWithTo = name+"_to";
//	if ($('#'+name).hasClass('toogle')) return;
	    $('#'+name).multiselect({
	    	sort: false,
	    	keepRenderingSort: false,
//	    	beforeMoveToRight: function($left, $right, $options) { disableButtons(name); return true; },
//	    	beforeMoveToLeft:function($left, $right, $options) { disableButtons(name); return true; },
//	    	beforeMoveUp: function($left, $right, $options) { disableButtons(name); return true; },
//	    	beforeMoveDown: function($left, $right, $options) { disableButtons(name); return true; },
	    	afterMoveToRight: function($left, $right, $options) {updateActionBarSwap(nameWithTo); updateSwapCounters(name);setChangeInputs(true)},
	    	afterMoveToLeft: function($left, $right, $options){updateActionBarSwap(nameWithTo);updateSwapCounters(name);setChangeInputs(true)},
//	    	afterMoveUp: function($left, $right, $options) {enableButtons(name);},
//	    	afterMoveDown: function($left, $right, $options) {enableButtons(name);}
	    });
	   
	
		$('#'+nameWithTo).click(function(){
			updateActionBarSwap(nameWithTo);
		});
}

function updateActionBarSwap(name) {
	var zTable= getTableRegistered(name);
	zTable.updateActionBar();
}
function enableButtons(name) {
    $("#"+name+"_zone_undo").prop( "disabled", false );
    $("#"+name+"_zone_rightAll").prop( "disabled", false );
    $("#"+name+"_zone_rightSelected").prop( "disabled", false );
    $("#"+name+"_zone_leftSelected").prop( "disabled", false );
    $("#"+name+"_zone_leftAll").prop( "disabled", false );
    $("#"+name+"_zone_redo").prop( "disabled", false );

}
function disableButtons(name) {
    $("#"+name+"_zone_undo").prop( "disabled", true );
    $("#"+name+"_zone_rightAll").prop( "disabled", true );
    $("#"+name+"_zone_rightSelected").prop( "disabled", true );
    $("#"+name+"_zone_leftSelected").prop( "disabled", true );
    $("#"+name+"_zone_leftAll").prop( "disabled", true );
    $("#"+name+"_zone_redo").prop( "disabled", true );
	
}
function inicializeCheckBox(name) {
		if (!(jQuery && jQuery.fn)) return;
		if ($('input[id="'+name+'"]').hasClass('toogle')) return;
		$('input[id="'+name+'"]').bootstrapToggle();

}

function initializeDataTimePicker(name) {
	if (!(jQuery && jQuery.fn)) 
		return;
	$('*[id="'+name+'"]').datetimepicker();
	$('*[id="'+name+'"]').off('dp.change');
	$('*[id="'+name+'"]').on('dp.change', function(e){ 
		 $('*[id="'+name+'"] input').trigger('change');
     });
}

function initializeDataTimePicker(name,options) {
	if (!(jQuery && jQuery.fn)) 
		return;
	$('*[id="'+name+'"]').datetimepicker(options);
	$('*[id="'+name+'"]').off('dp.change');
	$('*[id="'+name+'"]').on('dp.change', function(e){ 
		$('*[id="'+name+'"] input').trigger('change');
	 });
}

function openDataTimePicker(name){
//	$('#'+name+">input").click();
	$('*[id="'+name+'"]>input').data('DateTimePicker').toggle()
}
1

function initializeFromTo(from,to,value) {
    var pos = value.indexOf(",");
    if (pos!=-1) {
    	$('*[id="'+from+'"]').val(value.substring(0,pos));
    	$('*[id="'+to+'"]').val(value.substring(pos+1));
    }
}
function initializeIntervalDataTimePicker(name,from,to,options,formato,detectChanges) {
	if (!(jQuery && jQuery.fn)) 
		return;
    var obj=$('*[id="'+name+'"]').daterangepicker(options, function(start, end, label) {
    	$('*[id="'+name+'"]').val(start.format(formato) + ' - ' + end.format(formato));
    		 if (from!='') $('*[id="'+from+'"]').val(start.format(formato));
    		 if (to!='') $('*[id="'+to+'"]').val(end.format(formato));
    		 if (detectChanges) {
    			 setChangeInputs(true);
    		 }
     });

     var value = $('#'+name).val();
	 if (typeof value === 'undefined') return;
     var pos = value.indexOf(" - ");
     if (pos!=-1) {
    	 $('*[id="'+name+'"]').data('daterangepicker').setStartDate(value.substring(0,pos));
    	 $('*[id="'+name+'"]').data('daterangepicker').setEndDate(value.substring(pos+3));
         if (from!='') $('*[id="'+from+'"]').val(value.substring(0,pos));
         if (to!='') $('*[id="'+to+'"]').val(value.substring(pos+3));
     
     }



}

function isClosedIntervalDataTimePicker(name){
	return !$('.daterangepicker').is(":visible");
}
function openIntervalDataTimePicker(name){
	$('#'+name+'>input').click();
}



function clearIntervalDataTimePicker(name,from,to,detectChanges){
	$('#'+name+'>input').val('');
	if (from!='') $('#'+from).val('');
	if (to!='')  $('#'+to).val('');
	if (detectChanges)
		setChangeInputs(true);
}
function initializeTable(name,zcontainer,selLine) {
	$('#'+name).stickyTableHeaders({container: "#"+zcontainer});
    if (selLine) {
		$('#'+name+' tr').off("mouseover");
		$('#'+name+' tr').off("mouseout");
		$('#'+name+' tr').off("click");
		$('#'+name+' tr').mouseover(function(){
			rmo(this,true,event);
		});
		$('#'+name+' tr').mouseout(function(){
			rmo(this,false,event);
		});		
		$('#'+name+' tr').click(function(){
			rs(this,event);
			var zTable= getTableRegistered(name);
			zTable.updateActionBar();
		});
    } else {
		$('#'+name+' td').off("mouseover");
		$('#'+name+' td').off("mouseout");
		$('#'+name+' td').off("click");
		$('#'+name+' td').mouseover(function(){
			rmocell(this,true,event);
		});
		$('#'+name+' td').mouseout(function(){
			rmocell(this,false,event);
		});	
		$('#'+name+' td').click(function(){
			rsCell(this,event);;
		});
	}
    $('#'+name+' tr').each(function() {               
        $(this).attr('tabindex', '0');
    });
	$('#'+name+' td').off("keydown");
	$('#'+name+' td').keydown(function(){
		return gaOnKey(event, this);
	});

}

function fillMarked(input,multicheck,value) {
	addMark(multicheck,value);
	var inp = $('input[id="'+input+'"]');
	inp.bootstrapToggle('on');
}
function fillMark(input,multicheck,value) {
	var inp =  $('input[id="'+input+'"]');
	if (inp.is(':checked'))
		removeMark(multicheck,value);
	else
		addMark(multicheck,value);
}
function addMark(multicheck,value) {
	removeMark(multicheck,value);
	var mc =  $('input[id="'+multicheck+'"]');
	if (mc) 
		mc.val(mc.val()+","+value);
}


function removeMark(multicheck,value) {
	var mc = $('input[id="'+multicheck+'"]');
	if (mc) 
		mc.val(mc.val().replace(","+value,''));
}
function isMS() {

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))  // If Internet Explorer, return version number
    {
        return true
    }
  
    return false;
}
function removeOption(option) {
	if ( isMS())
		$(option).wrap( "<span>" ).parent().hide();
	else
		option.style.display = "none";
}
function showOption(option) {
	if ( isMS()) {
		if ( $(option).parent().is( "span" ) ){
			$(option).unwrap();
		}
	} else
		option.style.display = "block";	
	
}
function hideSelectItems(searchinputcomponent, selectzone) {
	var inputSearch = $('#' + searchinputcomponent).val();
	$('#' + selectzone + ' option').each(
	    function(index, option) {
		  if (option.label.toLowerCase().indexOf(inputSearch.toLowerCase()) == -1) {
			removeOption(option);
			
		  } else {
			showOption(option);
		}
	});
}
function hideSelectMetaItemsCheck(searchinputcomponent, meta, selectzone,operator,datatype) {
	var inputSearch =$('#' + searchinputcomponent).is(":checked");
	hideSelectMetaItems(inputSearch?"S":"N", meta, selectzone,operator,datatype);
}
function hideSelectMetaItemsLov(searchinputcomponent, meta, selectzone,operator,datatype) {
	var size = $('#' + searchinputcomponent+' option:selected').size();
	var inputSearch = size==0?'':$('#' + searchinputcomponent+' option:selected')[0].getAttribute('data-real_id');
	hideSelectMetaItems(inputSearch, meta, selectzone,operator,datatype);
}
function hideSelectMetaItemsCombo(searchinputcomponent, meta, selectzone,operator,datatype) {
	var size = $('#' + searchinputcomponent+' option:selected').size();
	var inputSearch = size==0?'':$('#' + searchinputcomponent+' option:selected')[0].getAttribute('data-real_id');
	hideSelectMetaItems(inputSearch, meta, selectzone,operator,datatype);
}
function hideSelectMetaItemsEdit(searchinputcomponent, meta, selectzone,operator,datatype) {
	var inputSearch = $('#' + searchinputcomponent).val();
	hideSelectMetaItems(inputSearch, meta, selectzone,operator,datatype);
}
function hideSelectMetaItemsRadio(searchinputcomponent, meta, selectzone,operator,datatype) {
	var inputSearch =$('#' + searchinputcomponent+' label.active input').val();
	if (inputSearch=='') return;
	hideSelectMetaItems(inputSearch, meta, selectzone,operator,datatype);
}
function hideSelectMetaItems(inputSearch, meta, selectzone,operator,datatype) {
	 if (typeof inputSearch === 'undefined') return;
	 if (inputSearch=='') return;
	$('#' + selectzone + ' option').each(
	  function(index, option) {
		  if (option.getAttribute('data-' + meta)==null){
			  removeOption(option);
			  return;
			  
		  }
		 var data = option.getAttribute('data-' + meta).toLowerCase();
		if (datatype==("LONG")||datatype==("JLONG")||datatype==("JINT")||datatype==("INT")) {
			if (operator=='>') {
				if (parseInt(data) > parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);
					

				}
			} else if (operator=='<') {
				if (parseInt(data) < parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);		
					

				}
			} else if (operator=='<>') {
				if (parseInt(data) != parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);			
				}
			} else if (operator=='>=') {
				if (parseInt(data) >= parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='<=') {
				if (parseInt(data) <= parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else {
				if (parseInt(data) == parseInt(inputSearch.toLowerCase())) {
		//			option.style.display = "block";
				} else {
					removeOption(option);
					

				}
			}

		} else if (datatype==("FLOAT")||datatype==("JFLOAT")) {
			if (operator=='>') {
				if (parseFloat(data) > parseFloat(inputSearch.toLowerCase())) {
			//		option.style.display = "block";
				} else {
					removeOption(option);
					

				}
			} else 	if (operator=='<') {
				if (parseFloat(data) < parseFloat(inputSearch.toLowerCase())) {
			//		option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='<>') {
				if (parseFloat(data) != parseFloat(inputSearch.toLowerCase())) {
			//		option.style.display = "block";
				} else {
					removeOption(option);
					

				}
			} else if (operator=='>=') {
				if (parseFloat(data) >= parseFloat(inputSearch.toLowerCase())) {
				//	option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='<=') {
				if (parseFloat(data) <= parseFloat(inputSearch.toLowerCase())) {
				//	option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else {
				if (parseFloat(data) == parseFloat(inputSearch.toLowerCase())) {
				//	option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			}

		} else {
			if (operator=='>') {
				if (data.localeCompare(inputSearch.toLowerCase())>0) {
			//		option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else 	if (operator=='<') {
				if (data.localeCompare(inputSearch.toLowerCase())<0) {
			//		option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='<>') {
				if (data.indexOf(inputSearch.toLowerCase()) == -1) {
			//		option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='>=') {
				if (data.localeCompare(inputSearch.toLowerCase())>0 || data==inputSearch.toLowerCase() ) {
				//	option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else if (operator=='<=') {
				if (data.localeCompare(inputSearch.toLowerCase())<0 || data==inputSearch.toLowerCase()) {
				//	option.style.display = "block";
				} else {
					removeOption(option);	
					

				}
			} else {
			

				if (data.indexOf(inputSearch.toLowerCase()) == -1) {
					removeOption(option);
					

				} else {
				//	option.style.display = "block";
				}
			}
		}
	});
}

function swapStartFilters(zcontrol,controlswap) {
	runAllControlConnect();
	var control = zcontrol;
	if (zcontrol.indexOf('form_')==0) control = zcontrol.substring(5);
	 setTimeout(function(){
		 updateSwapCounters(control);
		}, 500);
}

function updateSwapCounters(zcontrol) {
	var control = zcontrol;
	if (zcontrol.indexOf("_form_")!=-1) {
		control = zcontrol.substring(zcontrol.indexOf("_form_")+6,zcontrol.indexOf("_fd"));
	}
	var hid =$('#dgf_form_'+control+'_fd-swap_zone_to option:hidden').length;
	$('#dgf_form_'+control+'_fd-swap_label_hidden_a').html('<b>'+msgOptions+' ('+$('#dgf_form_'+control+'_fd-swap_zone option:visible').length+')</b>');
	$('#dgf_form_'+control+'_fd-swap_label_hidden_b').html(
			(hid>0?'<span style="color:red"><b>'+msgHideSelections+' ('+$('#dgf_form_'+control+'_fd-swap_zone_to option:hidden').length+')</b></span> ':'')+
			'<b>'+msgSelections+' ('+$('#dgf_form_'+control+'_fd-swap_zone_to option:visible').length+')</b>'
			);

}
function runUpKey(zcontrol) {
	var control = zcontrol;
	if (zcontrol.indexOf('form_')==0) control = zcontrol.substring(5);
	$('#dgf_form_'+control+'_fd-swap_zone option').each(function(index, option) {option.style.display = "block";});
	$('#dgf_form_'+control+'_fd-swap_zone_to option').each(function(index, option) {option.style.display = "block";});
	hideSelectItems('dgf_form_'+control+'_fd-swap_search_a','dgf_form_'+control+'_fd-swap_zone');
	hideSelectItems('dgf_form_'+control+'_fd-swap_search_b','dgf_form_'+control+'_fd-swap_zone_to');
	runControlConnect(control);
	updateSwapCounters(control)
}

function fillLov(controlFrom,controTo) {
    var value = $(controlFrom).val();
    if (value=='') return;
    if (value== $(controTo).val()) return;
    var $option = $("<option selected></option>").val(value).text($(controlFrom).text());
    $(controTo).append($option).trigger('change');
//    $(controTo).select2().trigger('change');
 
//    $(controTo).select2('open');
//    var $search = $(controTo).data('select2').dropdown.$search ||$(controTo).data('select2').selection.$search;
//
//    $search.val(value);
//    $search.trigger('input');
//    setTimeout(function() {  
//    	   var e = $.Event( "keypress", { which: 13 } );
//    	   $search.parent().find(".select2-search__field").trigger(e);
//     }, 500);

}


var waitingDialog = waitingDialog || (function ($) {
    'use strict';

	// Creating modal dialog's DOM
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="padding-top:15%; overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		/**
		 * Opens our dialog
		 * @param message Custom message
		 * @param options Custom options:
		 * 				  options.dialogSize - bootstrap postfix for dialog size, e.g. "sm", "m";
		 * 				  options.progressType - bootstrap postfix for progress bar type, e.g. "success", "warning".
		 */
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {}; 
			}
			if (typeof message === 'undefined') {
				message = 'Loading';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);

function maximizeImage(control,e,image) {

	var modal = $("#ModalMaximizeImage");
	if (modal.length==0) {
		    var MyHtml = '<div id="ModalMaximizeImage" class="modal fade">' +
		    ' <div class="modal-dialog ">' +
		        '<div class="modal-content">' +
		            ' <div class="modal-header">' +
		                '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>' +
		                '<h4 class="modal-title"> </h4>' +
		             '</div>' +
		            '<div class="modal-body">' +
		               '  <img not-to-enlarge="true" class="img-responsive" + src=""alt="...">' +
		            '</div>' +
		             '<div class="modal-footer">' +
		                 '<button type="button" class="btn btn-default" data-dismiss="modal">' +
		                    'Close' +
		                 '</button>' +
		             '</div>' +
		         '</div>' +
		     '</div>' +
		 '</div>';
		$("#modal-zone").append(MyHtml);
  	}

        var $input = $(control);
        var imgAlt = $input.attr("alt");
        $("#ModalMaximizeImage h4.modal-title").html(imgAlt);
        var img = this;
        var imageHeight = $input.height();
        var imagWidth = $input.width();
        var NewimgWidth = imagWidth * 2;
        var NewImgHeight = imageHeight * 2;
        var picSrc = image;
        $("#ModalMaximizeImage img").attr('src', picSrc);
        //set new image width
        $("div.modal-dialog").css("width", NewimgWidth);
        $("#ModalMaximizeImage img").width(NewimgWidth);
        //set new image height
        $("#ModalMaximizeImage img").height(NewImgHeight);        
        $("#ModalMaximizeImage").modal("show");
    



}
//


function Queue() {
	this.elements = [];
}
Queue.prototype.enqueue = function(e) {
	this.elements.push(e);
};
Queue.prototype.dequeue = function() {
	return this.elements.shift();
};
Queue.prototype.isEmpty = function() {
	return this.elements.length == 0;
};
Queue.prototype.peek = function() {
	return !this.isEmpty() ? this.elements[0] : undefined;
};
Queue.prototype.length = function() {
	return this.elements.length;
}
function showExpandedFieldset(comp) {
	var expand = $('#'+comp).hasClass('expand');
	if (expand) {
		 $('#'+comp).addClass('expand');
		 $('#'+comp).find('.collapsable').removeClass('collapse-formgroup');
		 $('#'+comp).find('.nocollapsable').addClass('collapse-formgroup');
		 $('#'+comp+'_button').html('<i class="fa fa-angle-double-up" data-toggle="tooltip" title="'+global_menosinfo+'"/>');
	} else {
		 $('#'+comp).removeClass('expand');
		 $('#'+comp+'_button').html('<i class="fa fa-angle-double-down" data-toggle="tooltip" title="'+global_masinfo+'"/>');
		 $('#'+comp).find('.collapsable').addClass('collapse-formgroup');
		 $('#'+comp).find('.nocollapsable').removeClass('collapse-formgroup');
	}
}
function toogleExpandedFieldset(comp) {
	var expand = $('#'+comp).hasClass('expand');
	if (expand) {
		 $('#'+comp).removeClass('expand');
		 $('#'+comp+'_button').html('<i class="fa fa-angle-double-down" data-toggle="tooltip" title="'+global_masinfo+'"/>');
		 $('#'+comp).find('.collapsable').addClass('collapse-formgroup');
		 $('#'+comp).find('.nocollapsable').removeClass('collapse-formgroup');
	} else {
		 $('#'+comp).addClass('expand');
		 $('#'+comp).find('.collapsable').removeClass('collapse-formgroup');
		 $('#'+comp).find('.nocollapsable').addClass('collapse-formgroup');
		 $('#'+comp+'_button').html('<i class="fa fa-angle-double-up" data-toggle="tooltip" title="'+global_menosinfo+'"/>');
		 
		
	}
}
function dropdowncomboinitialize(comp) {
	var componente=$('*[id="'+comp+'"]');
	var componentediv=$('*[id="'+comp+'_div"]');
	var componentedivdd=$('*[id="'+comp+'_div"] .dropdown-menu a');
	var componentedata=$('*[id="'+comp+'_data"]');
	var componentesearch=$('*[id="'+comp+'_search"]');

	componentediv.off('shown.bs.dropdown');
	componentedivdd.off('click');
	componentedivdd.click(function() {
		componente.val($(this).attr('data-value'));
		componentedata.val( $(this).text() );
		componente.change();
	  });
	componentediv.on('shown.bs.dropdown', function() {
		  componentesearch.focus();
	});

}
function dropdowncombofilter(comp) {
	var componentedivdd=$('*[id="'+comp+'_div"] .dropdown-menu div ul li a');
	var componentesearch=$('*[id="'+comp+'_search"]');

	var search =  componentesearch.val();
	componentedivdd.each(function(index) {
		 if ($(this).text().toLowerCase().indexOf(search.toLowerCase())==-1) {
			 $(this).hide();
		 } else {
			 $(this).show();
			 
		 }
	 });
}

function dropdownWinlovinitialize(comp,isopen,editable,owner,sction,provider,showkey) {
	var componente=$('*[id="'+comp+'"]');
	var componenteul=$('*[id="'+comp+'_ul"]');
	var componenteula=$('*[id="'+comp+'_ul"] a');
	var componentedata=$('*[id="'+comp+'_data"]');
	var componenteview=$('*[id="'+comp+'_view"]');
	var componentesearch=$('*[id="'+comp+'_search"]');
	var componentedd=$('*[id="'+comp+'_dropdown"]');
	var componentebutton=$('*[id="'+comp+'_button"]');
	var componenteisopen=$('*[id="'+comp+'_isopen"]');

	componentedd.off('shown.bs.modal');
	componentedd.off('show.bs.modal');
	componentedd.off('hide.bs.modal');
	componentedata.off('click');
	componentedata.off('keydown');
	componentesearch.off('input');
	componentesearch.off('keydown');
	  
	  if (componente.val()!='')
		  componenteview.show();
	  else
		  componenteview.hide();
	  
	  if (editable) {
		  componentedata.click(function() {
			  componentebutton.click();
		  });
		  componentedata.on('keydown', function(e) {
			  componentebutton.click();
		  });
		  componentesearch.on('input', function(e) {
				dropwinlovcombofilter(comp,owner,sction,provider,showkey);
		  });
		  componentesearch.on('keydown', function(e) {
			  if (e.which == 40) {
				  componenteul.find('a:first').focus();
			  }
		  });

	  }

	
	  dropdownwinlovEventsToList(comp);

	  componentedd.on('show.bs.modal', function(e) {
		  if ($(e.target).attr('id')==comp+'_dropdown') {

			  componentedd.append(
					  $('<div/>')
					    .attr("id", comp+'_backdroplocal')
					    .addClass("modal-backdroplocal-component")
				);
				var componenteback=$('*[id="'+comp+'_backdroplocal"]');
				  
			  componenteback.click(function() {
  				componentedd.modal('hide');
				});
		  }
	  });
//	  $('#'+comp+'_dropdown').on('click', function(e) {
	//	  consumeEvent(e);
//      });
	  componentedd.on('hide.bs.modal', function(e) {
		  if ($(e.target).attr('id')==comp+'_dropdown') {
				var componenteback=$('*[id="'+comp+'_backdroplocal"]');
			  componenteback.remove();
			  componenteisopen.val('N');
		  }
      });
	  componentedd.on('shown.bs.modal', function(e) {
		  if ($(e.target).attr('id')==comp+'_dropdown') {
			  $('body').removeClass('modal-open');
			  $('body').css('padding-right','0px');
			  componentesearch.focus();
			  componenteisopen.val('S');
		  }
		 });
	  
	  if (isopen) {
		  componentedd.modal('show');
	  }

}

function dropdownWinLovfillDataListJSON (comp,objectOwner,objectAction,provider) {
	var componentesearch=$('*[id="'+comp+'_search"]');

	var search = componentesearch.val();

	var cfg = {ajaxContainer: comp,
			dg_dictionary: sessionStorage.getItem('dictionary'),
			subsession: document.navform.subsession.value,
			src_uri: document.navform.src_uri.value,
			src_sbmt: document.navform.src_sbmt.value,
			dg_comboFormLov_id: comp,
			dg_object_owner: objectOwner,
			dg_object_action: objectAction,
			dg_table_provider: provider, 
			dg_source_control_id:comp, 
			dg_partial_info:'S',
			search: search,
	        type: 'public'
		};
		
		var filter = comp.lastIndexOf("-")==-1?"":comp.substring(0,comp.lastIndexOf("-"));
		cfg=collect(cfg,addRegisterToURLFromList(""));
		cfg=collect(cfg,addRegisterToUrl(filter,1000));
		

	return cfg;
}

function dropwinlovcombofilter(comp,objectOwner,objectAction,provider,showkey) {
	var componente=$('*[id="'+comp+'"]');
	var componenteul=$('*[id="'+comp+'_ul"]');
	var componenteula=$('*[id="'+comp+'_ul"] a');
	var componentedata=$('*[id="'+comp+'_data"]');
	var componenteview=$('*[id="'+comp+'_view"]');
	var componentesearch=$('*[id="'+comp+'_search"]');

	var search =  componentesearch.val();
	 if (search=='') {
		 var ul =  componenteul;
		 ul.html('');
		 return;
	 }
	 var data = dropdownWinLovfillDataListJSON(comp,objectOwner,objectAction,provider);
	var objAjax = $.ajax( {
         "type":     "post",
         "url":      "do-comboResponsiveFormLovAction",
         "data":     data,
         "dataType": "json",
         "cache":    true,
         "success":  function ( json) {
        	 dropwinlovresult(comp, json ,search,showkey);
        	 
         },
     	"error":  function (xhr, textStatus, errorThrown ) {
          	 console.log ( textStatus );
          	 
         }
     
     } );

	 
}

function dropdownwinlovclear(comp) {
	var componente=$('*[id="'+comp+'"]');
	var componenteula=$('*[id="'+comp+'_ul"] a');
	var componentedata=$('*[id="'+comp+'_data"]');
	var componenteview=$('*[id="'+comp+'_view"]');

	componente.val('');
	componentedata.val('');
	componente.trigger("change");
	componenteview.hide();

}

function dropwinlovresult(comp,json,originalsearch,showkey) {
	var componenteul=$('*[id="'+comp+'_ul"]');
	var componentesearch=$('*[id="'+comp+'_search"]');

	var search =  componentesearch.val();
	 if (search!=originalsearch) return;
	 var ul =  componenteul;
	 var shtml='';
	 $.each(json.results, function(i, item) {
	    shtml+='<li><a tabindex="0" data-value="'+item.id+'" data-real_id="'+item.real_id+'">'+(showkey?format(item).html():item.text)+'</a></li>';
	 });
	 ul.html(shtml);
	 dropdownwinlovEventsToList(comp);

}
function dropdownwinlovEventsToList(comp) {
	var componente=$('*[id="'+comp+'"]');
	var componenteula=$('*[id="'+comp+'_ul"] a');
	var componentedata=$('*[id="'+comp+'_data"]');
	var componentedd=$('*[id="'+comp+'_dropdown"]');
	var componenteview=$('*[id="'+comp+'_view"]');
	
	componenteula.off('click');
	componenteula.off('keydown');

	componenteula.click(function() {
		componente.val($(this).attr('data-value'));
		componentedata.val($(this).text());
		componentedd.modal('hide');
		componente.change();
		componenteview.show();
		
	});

	var li = componenteula, n = -1, ll = li.length - 1;
	componenteula.on('keydown', function(e) {
		var x = e.which;
		if (x === 40 || x === 39 || x === 38 || x === 37) {
			if (x === 40 || x === 39) {
				n++;
				if (n > ll)
					n = 0;
			} else if (x === 38 || x === 37) {
				n--;
				if (n < 0)
					n = ll;
			}

			var ci = li.get(n);
			$(ci).focus();
			consumeEvent(e);
		} else if (x === 32) {
			$(this).click();
			consumeEvent(e);
			return;
		}
	});
}

