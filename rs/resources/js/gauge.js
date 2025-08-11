var myFormat = {
	"decimal" : ",",
	"thousands" : ".",
	"grouping" : [ 3 ],
	"currency" : [ "$", "" ],
	"dateTime" : "%a %b %e %X %Y",
	"date" : "%d/%m/%Y",
	"time" : "%H:%M:%S",
	"periods" : [ "AM", "PM" ],
	"days" : [ "Domingo", "Lunes", "Martes", "Miercoles", "Jueves",
			"Viernes", "Sabado" ],
	"shortDays" : [ "Dom", "Lun", "Mar", "Mie", "Jue", "Vie", "Sab" ],
	"months" : [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
			"Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ],
	"shortMonths" : [ "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago",
			"Sep", "Oct", "Nov", "Dic" ]
};
var paleta = ["#a6cee3","#1f78b4","#b2df8a","#33a02c","#fb9a99","#e31a1c","#fdbf6f","#ff7f00","#cab2d6","#6a3d9a","#ffff99","#b15928","#8dd3c7","#ffffb3","#bebada","#fb8072","#80b1d3","#fdb462","#b3de69","#fccde5","#d9d9d9","#bc80bd","#ccebc5","#ffed6f","#1b9e77","#d95f02","#7570b3","#e7298a","#66a61e","#e6ab02","#a6761d","#666666","#fbb4ae","#b3cde3","#ccebc5","#decbe4","#fed9a6","#ffffcc","#e5d8bd","#fddaec","#f2f2f2"];

var es_AR = d3.locale(myFormat);

function Pie(place, leyenda, w, h, datos) {
    var testdata = datos;
    d3.scale.myColors = function () {
        return d3.scale.ordinal().range(paleta);
    };
    var $container = $('#' + place);
    var aspect = (w == -1 || h == -1) && h != 0 ? 0 : w / h;
    var widthp = $container.parent().width();
    var heightp = $container.parent().height();
    var width = $container.width();
    var height = $container.height();

    if (aspect == 0) {
        width = widthp;
        height = heightp;
    } else {
        if (w > widthp) { 
            width = widthp;
            height = widthp * aspect;
        } else {
            width = w;
            height = h;
        }
    }

    $container.width(width);
    $container.height(height);

    var size = width;
    width = size;
    height = size;
    $container.width(width);
    $container.height(height);
    var r = size / 2;
    var arc = d3.svg.arc().outerRadius(r);
    var aspect = width / height;

    nv.addGraph(function() {
        var chart = nv.models.pieChart()
            .x(function(d) { return d.key.length > ( widthp>1000?30:widthp>500?20:10) ? d.key.substring(0,( widthp>1000?30:widthp>500?20:10)) + "..." : d.key; })
            .y(function(d) { return d.y; })
            .color(d3.scale.myColors().range())
            .labelThreshold(.03) 
            .labelType("percent")
            .donut(true)
            .donutRatio(0);

        chart.tooltip.contentGenerator(function(d) {
            return '<div style="padding: 5px;">' +
                     '<strong>' + d.data.key + '</strong><br/>' +
                     'Valor: ' + d.data.y +
                   '</div>';
        });

        nv.utils.windowResize(chart.update);

        d3.select("#" + place)
           .datum(testdata)
           .transition().duration(1200)
           .attr('width', "100%")
           .attr('height', "100%")
           .attr('viewBox', '0 0 ' + size + ' ' + size)
           .attr('preserveAspectRatio', 'xMinYMin')
           .call(chart);

        d3.selectAll(".nv-label text")
            .attr("transform", function(d) {
                if (r === 0) return "translate(0,0)";
                d.innerRadius = -34;
                d.outerRadius = r;
                var centroid = arc.centroid(d);
                if (!isFinite(centroid[0]) || !isFinite(centroid[1])) {
                    return "translate(0,0)";
                }
                return "translate(" + centroid[0] + "," + centroid[1] + ")";
            })
            .attr("text-anchor", "middle")
            .style("font-size", "1em");

        // 📌 Ajuste de la leyenda en DOS COLUMNAS
        nv.utils.windowResize(function() {
            var series = d3.selectAll(".nv-legend .nv-series");
            var totalItems = series.size();
            var columns = 2; 
            var rows = Math.ceil(totalItems / columns);
            var columnWidth = width / columns; 

            series.each(function(d, i) {
                var col = i % columns;
                var row = Math.floor(i / columns);
                var xPosition = col * columnWidth; 
                var yPosition = row * 20; 
                
                d3.select(this)
                    .attr("transform", `translate(${xPosition},${yPosition})`);
            });

            // Ajustamos el tamaño del contenedor de la leyenda
            d3.select(".nv-legendWrap")
                .attr("width", width)
                .attr("height", rows * 20 + 10);
        });

        if (leyenda != '') {
            var fontSize = leyenda.length < 10 ? Math.round(size / 9) :
                           (leyenda.length < 20 ? Math.round(size / 18) : Math.round(size / 27));
            d3.select("#" + place).append("svg:text")
                .attr("x", size / 2)
                .attr("y", size / 2)
                .attr("dy", fontSize / 2)
                .attr("text-anchor", "middle")
                .text(leyenda)
                .style("font-size", fontSize + "px")
                .style("fill", "#333")
                .style("stroke-width", "0px");
        }
        return chart;
    });
}



function Stacked(place,w,h,datos)
{
    var $container = $('#'+place);
    var aspect = ( w==-1||h==-1) &&h!=0?0:w/h;
    var widthp = $container.parent().width();
    var heightp = $container.parent().height();
    var width = $container.width();
    var height = $container.height();
//    var size = Math.max(width, height);
    if (aspect==0) {// size parent
     width=widthp;
     height=heightp;
    } else {
    	if (w>widthp) { // es mayor al espacio me adapto
            width=widthp;
            height=widthp*aspect;
    		
    	} else {
            width=w;
            height=h;
    		
    	}
    	
    }
  
    $container.width(width);
    $container.height(height);

//    var $container = $('#'+place).parent();
//    var width = $container.width();
//    var height = $container.height();
//    $('#'+place).width(width);
//    $('#'+place).height(height);
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(paleta);
    };
      
	   var histcatexplong = datos;
         var colors = paleta;
         var chart;
         nv.addGraph(function() {
             chart = nv.models.stackedAreaChart()
                 .useInteractiveGuideline(true)
                 .x(function(d) { return d[0] })
                 .y(function(d) { return d[1] })
                 .controlLabels({stacked: "Stacked"})
                 .duration(300)
             	 .color(d3.scale.myColors().range());
     	         chart.xAxis.tickFormat(d3.format(',f'));
        	     chart.xAxis.tickFormat(function(d) {
        	          return d3.time.format('%m-%y')(new Date(d))
        	      });
        	     chart.xScale(d3.time.scale());
     	    
             chart.yAxis.tickFormat(d3.format(',.2f'));
             chart.legend.vers('furious');
             d3.select('#'+place)
                 .datum(histcatexplong)
                 .transition().duration(1000)
                 .call(chart)
                 .each('start', function() {
                     setTimeout(function() {
                         d3.selectAll('#'+place+' *').each(function() {
                             if(this.__transition__)
                                 this.__transition__.duration = 1;
                         })
                     }, 0)
                 });
             nv.utils.windowResize(chart.update);
             return chart;
         });
	     
}



function Gauge(placeholderName, configuration)
{
	this.placeholderName = placeholderName;
	
	var self = this; // for internal d3 functions
	
	this.configure = function(configuration)
	{
		this.config = configuration;
		
		this.config.size = this.config.size *.92;
		 
		this.config.raduis = this.config.size * 0.92 / 2;
		this.config.cx = this.config.size / 2;
		this.config.cy = this.config.size / 2;
		
		this.config.min = undefined != configuration.min ? configuration.min : 0; 
		this.config.max = undefined != configuration.max ? configuration.max : 100; 
		this.config.range = this.config.max - this.config.min;
		
		this.config.majorTicks = configuration.majorTicks || 5;
		this.config.minorTicks = configuration.minorTicks || 2;
		this.config.value = configuration.value;
		
		this.config.greenColor 	= configuration.greenColor || "#109618";
		this.config.yellowColor = configuration.yellowColor || "#FF9900";
		this.config.redColor 	= configuration.redColor || "#DC3912";
		
		this.config.transitionDuration = configuration.transitionDuration || 500;
	}

	this.render = function()
	{
		this.body = d3.select("#" + this.placeholderName)
							.append("svg:svg")
							.attr("class", "gauge")
							.attr("width", this.config.size)
							.attr("height", this.config.size);
		
		this.body.append("svg:circle")
					.attr("cx", this.config.cx)
					.attr("cy", this.config.cy)
					.attr("r", this.config.raduis)
					.style("fill", "#ccc")
					.style("stroke", "#000")
					.style("stroke-width", "0.5px");
					
		this.body.append("svg:circle")
					.attr("cx", this.config.cx)
					.attr("cy", this.config.cy)
					.attr("r", 0.9 * this.config.raduis)
					.style("fill", "#fff")
					.style("stroke", "#e0e0e0")
					.style("stroke-width", "2px");
					
		for (var index in this.config.greenZones)
		{
			this.drawBand(this.config.greenZones[index].from, this.config.greenZones[index].to, self.config.greenColor);
		}
		
		for (var index in this.config.yellowZones)
		{
			this.drawBand(this.config.yellowZones[index].from, this.config.yellowZones[index].to, self.config.yellowColor);
		}
		
		for (var index in this.config.redZones)
		{
			this.drawBand(this.config.redZones[index].from, this.config.redZones[index].to, self.config.redColor);
		}
		
		if (undefined != this.config.label)
		{
			var fontSize = this.config.label.length<10?Math.round(this.config.size / 9):
				(this.config.label.length<20?Math.round(this.config.size / 18):Math.round(this.config.size / 27));
			this.body.append("svg:text")
						.attr("x", this.config.cx)
						.attr("y", this.config.cy / 2 + fontSize / 2)
						.attr("dy", fontSize / 2)
						.attr("text-anchor", "middle")
						.text(this.config.label)
						.style("font-size", fontSize + "px")
						.style("fill", "#333")
						.style("stroke-width", "0px");
		}
		
		var fontSize = Math.round(this.config.size / 16);
		var majorDelta = this.config.range / (this.config.majorTicks - 1);
		for (var major = this.config.min; major <= this.config.max; major += majorDelta)
		{
			var minorDelta = majorDelta / this.config.minorTicks;
			for (var minor = major + minorDelta; minor < Math.min(major + majorDelta, this.config.max); minor += minorDelta)
			{
				var point1 = this.valueToPoint(minor, 0.75);
				var point2 = this.valueToPoint(minor, 0.85);
				
				this.body.append("svg:line")
							.attr("x1", point1.x)
							.attr("y1", point1.y)
							.attr("x2", point2.x)
							.attr("y2", point2.y)
							.style("stroke", "#666")
							.style("stroke-width", "1px");
			}
			
			var point1 = this.valueToPoint(major, 0.7);
			var point2 = this.valueToPoint(major, 0.85);	
			
			this.body.append("svg:line")
						.attr("x1", point1.x)
						.attr("y1", point1.y)
						.attr("x2", point2.x)
						.attr("y2", point2.y)
						.style("stroke", "#333")
						.style("stroke-width", "2px");
			
			if (major == this.config.min || major == this.config.max)
			{
				var point = this.valueToPoint(major, 0.63);
				
				this.body.append("svg:text")
				 			.attr("x", point.x)
				 			.attr("y", point.y)
				 			.attr("dy", fontSize / 3)
				 			.attr("text-anchor", major == this.config.min ? "start" : "end")
				 			.text(major)
				 			.style("font-size", fontSize + "px")
							.style("fill", "#333")
							.style("stroke-width", "0px");
			}
		}
		
		var pointerContainer = this.body.append("svg:g").attr("class", "pointerContainer");
		
		var midValue = (this.config.min + this.config.max) / 2;
		
		var pointerPath = this.buildPointerPath(midValue);
		
		var pointerLine = d3.svg.line()
									.x(function(d) { return d.x })
									.y(function(d) { return d.y })
									.interpolate("basis");
		
		pointerContainer.selectAll("path")
							.data([pointerPath])
							.enter()
						    .append("svg:path")
							.attr("d", pointerLine)
							.style("fill", "#dc3912")
							.style("stroke", "#c63310")
							.style("fill-opacity", 0.7)
					
		pointerContainer.append("svg:circle")
							.attr("cx", this.config.cx)
							.attr("cy", this.config.cy)
							.attr("r", 0.12 * this.config.raduis)
							.style("fill", "#4684EE")
							.style("stroke", "#666")
							.style("opacity", 1);
		
		var fontSize = Math.round(this.config.size / 10);
		pointerContainer.selectAll("text")
							.data([midValue])
							.enter()
							.append("svg:text")
							.attr("x", this.config.cx)
							.attr("y", this.config.size - this.config.cy / 4 - fontSize)
							.attr("dy", fontSize / 2)
							.attr("text-anchor", "middle")
							.style("font-size", fontSize + "px")
							.style("fill", "#000")
							.style("stroke-width", "0px");
		
		
		
		this.redraw(this.config.min, 0);
	}
	
	this.buildPointerPath = function(value)
	{
		var delta = this.config.range / 13;
		
		var head = valueToPoint(value, 0.85);
		var head1 = valueToPoint(value - delta, 0.12);
		var head2 = valueToPoint(value + delta, 0.12);
		
		var tailValue = value - (this.config.range * (1/(270/360)) / 2);
		var tail = valueToPoint(tailValue, 0.28);
		var tail1 = valueToPoint(tailValue - delta, 0.12);
		var tail2 = valueToPoint(tailValue + delta, 0.12);
		
		return [head, head1, tail2, tail, tail1, head2, head];
		
		function valueToPoint(value, factor)
		{
			var point = self.valueToPoint(value, factor);
			point.x -= self.config.cx;
			point.y -= self.config.cy;
			return point;
		}
	}
	
	this.drawBand = function(start, end, color)
	{
		if (0 >= end - start) return;
		
		this.body.append("svg:path")
					.style("fill", color)
					.attr("d", d3.svg.arc()
					.startAngle(this.valueToRadians(start))
					.endAngle(this.valueToRadians(end))
					.innerRadius(0.65 * this.config.raduis)
					.outerRadius(0.85 * this.config.raduis))
					.attr("transform", function() { return "translate(" + self.config.cx + ", " + self.config.cy + ") rotate(270)" });
	}

	this.getValue = function()
	{
		return this.config.value;
	}
	this.redraw = function(value, transitionDuration)
	{
		var pointerContainer = this.body.select(".pointerContainer");
		
		pointerContainer.selectAll("text").text(Math.round(value));
		
		var pointer = pointerContainer.selectAll("path");
		pointer.transition()
					.duration(undefined != transitionDuration ? transitionDuration : this.config.transitionDuration)
					// .delay(0)
					// .ease("linear")
					// .attr("transform", function(d)
					.attrTween("transform", function()
					{
						var pointerValue = value;
						if (value > self.config.max) pointerValue = self.config.max + 0.02*self.config.range;
						else if (value < self.config.min) pointerValue = self.config.min - 0.02*self.config.range;
						var targetRotation = (self.valueToDegrees(pointerValue) - 90);
						var currentRotation = self._currentRotation || targetRotation;
						self._currentRotation = targetRotation;
						
						return function(step) 
						{
							var rotation = currentRotation + (targetRotation-currentRotation)*step;
							return "translate(" + self.config.cx + ", " + self.config.cy + ") rotate(" + rotation + ")"; 
						}
					});
	}
	
	this.valueToDegrees = function(value)
	{
		// thanks @closealert
		// return value / this.config.range * 270 - 45;
		return value / this.config.range * 270 - (this.config.min / this.config.range * 270 + 45);
	}
	
	this.valueToRadians = function(value)
	{
		return this.valueToDegrees(value) * Math.PI / 180;
	}
	
	this.valueToPoint = function(value, factor)
	{
		return { 	x: this.config.cx - this.config.raduis * factor * Math.cos(this.valueToRadians(value)),
					y: this.config.cy - this.config.raduis * factor * Math.sin(this.valueToRadians(value)) 		};
	}
	
	// initialization
	this.configure(configuration);	
}









function addAsterPlot(place,zwidth,zheight,data) {
       var $container = $('#'+place);
       var width = $container.width();
       var height = $container.height();
       var size = Math.min(width, height);

    radius = Math.min(width, height) / 2,
    innerRadius = 0.3 * radius;
	var pie = d3.layout.pie()
		    .sort(null)
		    .value(function(d) { return d.width; });
    var tip = d3.tip()
		    .attr('class', 'd3-tip')
		    .offset([0, 0])
		    .html(function(d) {
		    	return d.data.label + ": <span style='color:orangered'>" + parseFloat(d.data.score).toFixed(2) + "</span>";
		    });
    var arc = d3.svg.arc()
		  .innerRadius(innerRadius)
		  .outerRadius(function (d) { 
		    return (radius - innerRadius) * (d.data.score / 100.0) + innerRadius;
		  });
    var outlineArc = d3.svg.arc()
        .innerRadius(innerRadius)
        .outerRadius(radius);
    var svg = d3.select("#"+place).append("svg")
	    .attr("width", width)
	    .attr("height", height)
	    .append("g")
	    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
  	svg.call(tip);
	var path = svg.selectAll(".solidArc")
	      .data(pie(data))
	      .enter().append("path")
	      .attr("fill", function(d) { return d.data.color; })
	      .attr("class", "solidArc")
	      .attr("stroke", "gray")
	      .attr("d", arc)
	      .on('mouseover', tip.show)
	      .on('mouseout', tip.hide);
	var outerPath = svg.selectAll(".outlineArc")
	      .data(pie(data))
	      .enter().append("path")
	      .attr("fill", "none")
	      .attr("stroke", "gray")
	      .attr("class", "outlineArc")
	      .attr("d", outlineArc);
	var score = data.reduce(function(a, b) {return a + (b.score * b.weight); }, 0) /  data.reduce(function(a, b) { return a + b.weight; }, 0);
    svg.append("svg:text")
	    .attr("class", "aster-score")
	    .attr("dy", ".35em")
	    .attr("text-anchor", "middle")
	    .text(Math.round(score));
							
}

function addSerieTemporal(place,title,data,start,end,image,withFocus,withHora) {
      var $container = $('#'+place).parent();
       var width = $container.width();
       var swidth = $container.css('width');
       var height = $container.height();
       $('#'+place).width(swidth==''?width:swidth);
       $('#'+place).height(height);
       
 $container.attr('data-original-height', height);
	nv.addGraph(function() { 
	    var chart;
	    if (withFocus=='S')
	    	chart = nv.models.lineWithFocusChart();
	    else
	    	chart = nv.models.lineChart();
	    
        if (start!=-1)    
        	chart.brushExtent([start,end]);

	 //   chart.xAxis.tickFormat(d3.format(',f'));"
        if (withHora) {
			chart.xAxis.tickFormat(function(d) {
				return d3.time.format('%d-%m %H:%M')(new Date(d));
			});
		} else {
		    chart.xAxis.tickFormat(function(d) {
		          return d3.time.format('%d-%m-%y')(new Date(d))
		      });
		}
	    chart.xScale(d3.time.scale());
	   
	    if (withFocus=='S') {
		    chart.focusHeight(50 + 20);
		    chart.focusMargin({ "bottom": 20 + 20 });
	        if (withHora) {
		    chart.x2Axis.tickFormat(function(d) {
		          return d3.time.format('%d-%m-%y')(new Date(d))
		      }).axisLabel(title);
	        } else {
	        	chart.x2Axis.tickFormat(function(d) {
				return d3.time.format('%d-%m %H:%M')(new Date(d));
	        	}).axisLabel(title);
	        }
		  /*  chat.selectAll("dot")	
	            .data(data)			
	        	.enter().append("circle")								
		        .attr("r", 5)		
		        .attr("cx", function(d) { return x(d.date); })		 
		        .attr("cy", function(d) { return y(d.close); })		
		        .on("mouseover", function(d) {		
		            div.transition()		
		                .duration(200)		
		                .style("opacity", .9);		
		            div	.html("HOLA <br/>" );	
		                .style("left", (d3.event.pageX) + "px")		
		                .style("top", (d3.event.pageY - 28) + "px");	
		            })					
		        .on("mouseout", function(d) {		
		            div.transition()		
		                .duration(500)		
		                .style("opacity", 0);	
		        });*/
		    chart.yAxis.tickFormat(function (d) {
		        var array = ['','k','M','MM'];
		        var i=0;
		        while (d > 1000 && i<array.length)
		        {
		            i++;
		            d = d/1000;
		        }

		        d = d+array[i];

		        return d;});
		    chart.y2Axis.tickFormat(function (d) {
		        var array = ['','k','M','MM'];
		        var i=0;
		        while (d > 1000 && i<array.length)
		        {
		            i++;
		            d = d/1000;
		        }

		        d = d+array[i];

		        return d;});
			chart.yAxis.tickFormat(es_AR.numberFormat(',.2f'));
			chart.y2Axis.tickFormat(es_AR.numberFormat(',.2f'));
		    chart.useInteractiveGuideline(true);
   	    }
	    
	    
	    var svg=d3.select('#'+place+' svg')
	        .datum(data)
	        .call(chart);
		    nv.utils.windowResize(chart.update);
		        
		    if (image!=null) {
			    svg.append("svg:image")
			    .attr('x',0)
			    .attr('y',0)
			    .attr('width', 500)
			    .attr('height', 343)
			    .attr('opacity',.2)
			    .attr("xlink:href",image)
		    }
		$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		    var target = $(e.target).attr("href"); // ID de la pestaña activada
		    var svg = $(target).find('svg');
	   		var originalHeight = $('#'+place).parent().attr('data-original-height'); // Recupera la altura correcta
   
	    
		    if (svg.length > 0) {
		        svg.width($(target).width()); // Ajusta el tamaño del SVG
		        svg.height(originalHeight);
		
		        d3.select(svg[0]).call(chart); // Reaplica el gráfico con los nuevos tamaños
		        chart.update();
		    }
		});

	    return chart;
	});
	
	
}
function addMultiBar(place, title, data, w, h, reduce) {
    var $container = $('#' + place);
    var width = $container.width();
    var height = $container.height();
    var size = Math.max(width, height);
    width = size;
    height = size * (w / h);
    $container.width(width);
    $container.height(height);

    d3.scale.myColors = function () {
        return d3.scale.ordinal().range(paleta);
    };

    nv.addGraph({
        generate: function () {
            var chart = nv.models.multiBarChart()
                .width(width)
                .height(height)
                .stacked(false)
                .rotateLabels(-45)
                .showLegend(width > 500)
                .color(d3.scale.myColors().range())
                .reduceXTicks(reduce);

            // ✅ Formato del eje Y con K y M
            chart.yAxis
                .tickFormat(function (d) {
                    if (d >= 1e6) return (d / 1e6).toFixed(1).replace('.0', '') + 'M';
                    if (d >= 1e3) return (d / 1e3).toFixed(1).replace('.0', '') + 'K';
                    return d;
                });

            chart.dispatch.on('renderEnd', function () {
                console.log('Render Complete');
            });

            var svg = d3.select('#' + place + ' svg').datum(data);
            svg.transition().duration(0).call(chart);

            return chart;
        },
    });
}


function addMultiBar(place, title, data, w, h, rotate, reduce, myColors, isStacked, showControls, showLegend, margen) {
    var $container = $('#' + place);
    var aspect = (w == -1 || h == -1) && h != 0 ? 0 : w / h;
    var widthp = $container.parent().width();
    var heightp = $container.parent().height();
    var width = $container.width();
    var height = $container.height();

    if (aspect == 0) {
        width = widthp;
        height = heightp;
    } else {
        if (w > widthp) {
            width = widthp;
            height = widthp * aspect;
        } else {
            width = w;
            height = h;
        }
    }

    $container.width(width);
    $container.height(height);

    nv.addGraph({
        generate: function () {
            var chart = nv.models.multiBarChart().margin({
                left: margen,
                bottom: margen
            })
                .width(width)
                .height(height)
                .rotateLabels(rotate ? -90 : 0)
                .stacked(isStacked)
                .showControls(showControls)
                .showLegend(showLegend)
                .reduceXTicks(reduce)
                .color(myColors);

            // ✅ Formateo de valores del eje Y: K y M
            chart.yAxis.tickFormat(function (d) {
                if (d >= 1e6) return (d / 1e6).toFixed(1).replace('.0', '') + 'M';
                if (d >= 1e3) return (d / 1e3).toFixed(1).replace('.0', '') + 'K';
                return d;
            });

            var svg = d3.select('#' + place + ' svg').datum(data);
            svg.transition().duration(0).call(chart);
            nv.utils.windowResize(chart.update);

            return chart;
        }
    });
}


function addDonutPie(place, title, data, width, height, reduce, myColors) {

	d3.scale.myColors = function() {
		return d3.scale.ordinal().range(myColors);
	};

	// Donut chart example
	nv.addGraph(function() {
		var chart = nv.models.pieChart().x(function(d) {
			return d.label
		}).y(function(d) {
			return d.value
		}).showLabels(true) // Display pie labels
		.labelThreshold(.05) // Configure the minimum slice size for labels
		// to show up
		.labelType("percent") // Configure what type of data to show in the
		// label. Can be "key", "value" or "percent"
		.donut(true) // Turn on Donut mode. Makes pie chart look tasty!
		.donutRatio(0.35) // Configure how big you want the donut hole size to
        .showLegend(width>500)
		// be.
		.color(d3.scale.myColors().range());
		;

		d3.select('#' + place + ' svg').datum(data).transition().duration(0)
				.call(chart);

		nv.utils.windowResize(chart.update);

		svg.append("svg:image").attr('x', 0).attr('y', 0).attr('width', 300)
				.attr('height', 200).attr('opacity', .2)

		return chart;
	});

}

function addDonut(place,title,data,width,height) {	
    var legendSpace = 4;
    var rectSize = 8;
	var svg = d3.select('#'+place).append("svg").attr("width",width).attr("height",height);

    //Add legend
    data.forEach(function (d, i) {
        svg.append("rect")
            .attr("transform", "translate(0," + i * (rectSize + legendSpace) + ")")
            .attr("class", "rect")
            .attr("width", rectSize)
            .attr("height", rectSize)
            .attr("x", 10)       //x-axis of rect
            .attr("y", 10)     //y-axis of rect
            .style("stroke", "#000000")
            .style("stroke-width", .25)
            .style("fill", d.color);
        svg.append("text")
            .attr("class", "legend")
            .attr("x", rectSize + legendSpace)
            .attr("y", (i * legendSpace) + (i * rectSize))
            .attr("dx", 10)      //x-axis of text
            .attr("dy", 18)    //y-axis of text
            .style("font-size", "10px")
            .text(d.label);
    });
    
	svg.append("g")
	.data(data)
    .attr("id",place);

    Donut3D.draw(place, data, width/2, (height-10)/2, (width/2)-30, ((height-10)/2)-30, 20, 0.4);



}
 

function addBullet(place,data,zwidth,zheight) {
	var margin = {top: 2, right: 2, bottom: 2, left: 2},
    width = zwidth - margin.left - margin.right,
    height = zheight - margin.top - margin.bottom;
    d3.scale.myColors = function() {
        return d3.scale.ordinal().range(paleta);
    };
   
	nv.addGraph({
	    generate: function() {
	    	  var chart = nv.models.bulletChart()
	          .height(height)
	    	  .color(d3.scale.myColors().range());

	    	  if (zwidth!=0)
	    		  chart.width(width);
	    	  
	    	  d3.select('#'+place+' svg')
	    	      .datum(data)
	    	      .transition().duration(1000)
	    	      .call(chart);
	
	        return chart;
	    },

	});

}

var arrMessage = null;
function unRegister3dMessage() {
	arrMessage = new Array();
}
function get3DMessage() {
	if (arrMessage==null) arrMessage=new Array();
	return arrMessage;
}	

var arrRender3D = null;
function unRegisterRender3d() {
	arrRender3D = new Array();
}

function isRender3DRegistered(render3DId) {
	var arr=getRender3D();
	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    if (currAss!=null && currAss[0]==render3DId) {
		  return true;
		}
	  }
	  return false;
}
function getRender3D() {
	if (arrRender3D==null) arrRender3D=new Array();
	return arrRender3D;
}	
function getOneRender3D(name) {
	var arr=getRender3D();
	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    if (currAss!=null && currAss[0]==name) {
	    	return currAss;
	    }
	}
	return null;
}

function addRender3D(name,scene,camera,raycaster, container, renderer,controls,ancho,alto) {
	var arr=getRender3D();
	var ca=new Array();
	ca[0]=name;
	ca[1]=scene;
	ca[2]=camera;
	ca[3]=raycaster;
	ca[4]=container;
	ca[5]=renderer;
	ca[6]=new Array();
	ca[7]=new Array();
	ca[8]=null;
	ca[9]=null;
	ca[10]=null;
	ca[11]=null;
	ca[12]=null;
	ca[13]=controls;
	ca[14]=null;
	ca[15]=ancho;
	ca[16]=alto;
	arr[arr.length]=ca;
	
	return ca;
}
 
function addMessage3D(object,posx,posy,posz,grx,gry,message) {
	var arr=get3DMessage();
	var ca=new Array();
	ca[0]=object;
	ca[1]=posx;
	ca[2]=posy;
	ca[3]=posz;
	ca[4]=message;
	ca[5]=grx;
	ca[6]=gry;
	
	arr[arr.length]=ca;
	
	return ca;
}
 
function push3DMessage(scene3d,object) {
	var scene=scene3d[1];
	var arr=get3DMessage();
	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    if (currAss!=null && currAss[0]==object) {
			
	    	if (scene3d[12]!=null)
				scene.remove(scene3d[12]);

	    	
	    	scene3d[12] = makeTextSprite ( currAss[4], 
	                  { fontsize: 100, borderColor: {r:255, g:0, b:0, a:1.0},
	                  backgroundColor: {r:255, g:100, b:100, a:0.8} }  );
	    	scene3d[12].position.set(currAss[1],currAss[2],currAss[3]);
			scene.add( scene3d[12] );
			return currAss;
	    }
	}
	return null;
}


var mouse = new THREE.Vector2();
function createScene(namecontainer) {
	var container;
	var parentcontainer;
	var camera, scene, raycaster;

	parentcontainer = document.getElementById(namecontainer);
	unRegister3dMessage();

	container = document.createElement( 'div' );
	parentcontainer.appendChild( container );

	camera = new THREE.PerspectiveCamera( 70, parentcontainer.getBoundingClientRect().width / parentcontainer.getBoundingClientRect().height, 1, 10000 );
	camera.position.x = 500 ;
	camera.position.y = 2000 ;
	camera.position.z = 2000 ;
	// camera.rotation.y = Math.PI/4 ;
	controls = new THREE.TrackballControls( camera );

// controls.rotateSpeed = 1.0;
// controls.zoomSpeed = 1.2;
// controls.panSpeed = 0.8;

	controls.noZoom = false;
	controls.noPan = false;

	controls.staticMoving = true;
// controls.dynamicDampingFactor = 0.3;

	controls.keys = [ 65, 83, 68 ];

 	controls.addEventListener( 'change', renderAll );


	scene = new THREE.Scene();
	// scene.fog = new THREE.Fog( 0xdddddd, 250, 1400 );

	
	var light = new THREE.DirectionalLight( 0xffffff, 1 );
	light.position.set( 1, 1, 1 ).normalize();
	scene.add( light );
// var light2 = new THREE.DirectionalLight( 0xffffff, 1 );
// light.position.set( 1, 0, 1 ).normalize();
// scene.add( light2 );
// var light3 = new THREE.DirectionalLight( 0xffffff, 1 );
// light.position.set( 1, 1, 0 ).normalize();
// scene.add( light3 );

	raycaster = new THREE.Raycaster();

	var renderer = new THREE.WebGLRenderer();
	renderer.setClearColor( 0xf0f0f0 );
	renderer.setPixelRatio( window.devicePixelRatio );
	renderer.setSize( parentcontainer.getBoundingClientRect().width , parentcontainer.getBoundingClientRect().height);
	renderer.sortObjects = false;

	var resp= addRender3D(namecontainer,scene,camera,raycaster,container,renderer,controls,parentcontainer.getBoundingClientRect().width , parentcontainer.getBoundingClientRect().height);
	animate();
	return resp;
}

function addCube(scene3d,zcolor,posx,posy,posz,scalx,scaly,scalz,grx,gry,message,center) {

	var scene = scene3d[1];
	var camera = scene3d[2];
	var container = scene3d[4];
	var renderer = scene3d[5];
	
	var cubeMaterialArray = [];
	// order to add materials: x+,x-,y+,y-,z+,z-
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: 0x222288  } ) );
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: zcolor } ) );
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: zcolor } ) );
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: zcolor } ) );
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: 0x3333ff } ) );
	cubeMaterialArray.push( new THREE.MeshBasicMaterial( { color: 0x8833ff } ) );
	var cubeMaterials = new THREE.MeshFaceMaterial( cubeMaterialArray );
	var cubeGeometry = new THREE.CubeGeometry( scalx, scaly, scalz, 1, 1, 1 );
	cube = new THREE.Mesh( cubeGeometry,  cubeMaterials );
	cube.position.set(posx+(scalx/2), posy+(scaly/2), posz+(scalz/2));
	scene.add( cube );	
	
	if (center) { 
		scene3d[14]=cube.position;
		camera.position.x = scene3d[14].x * 1.5;
		camera.position.y = 500 ;
		camera.position.z = (scene3d[14].z * 2.5)+420;
	}
	if (message!="")
		addMessage3D(cube,posx-(scalx/2),posy+scaly+40<200?200:posy+scaly+40,posz-(scalz/2),grx,gry,message)
}

function addCubeSimple(scene3d,zcolor,posx,posy,posz,scalx,scaly,scalz,grx,gry) {
	var scene = scene3d[1];
	var container = scene3d[4];
	var renderer = scene3d[5];
	var vectorX = scene3d[6];
	var vectorY = scene3d[7];
	var cubeGeometry = new THREE.CubeGeometry( scalx, scaly, scalz, 1, 1, 1 );
	cube = new THREE.Mesh( cubeGeometry,  new THREE.MeshLambertMaterial( { color: zcolor }  ));
	cube.position.set(posx+(scalx/2), posy+(scaly/2), posz+(scalz/2));
	scene.add( cube );	

	if (grx!=0) vectorX[grx-1]=cube;
	if (gry!=0) vectorY[gry-1]=cube;
}

function addText(scene3d,font,color,text,sizex,sizey,posx,posy,posz,rotx,roty,rotz) {
	var scene = scene3d[1];
	var container = scene3d[4];
	var renderer = scene3d[5];
	
    var Text2D = THREE_Text.Text2D;

	var text = new Text2D(text, { font: font, fillStyle: color, antialias: true })
	text.position.set(posx,posy,posz);
	text.rotation.x = rotx;
	text.rotation.y = roty;
	text.rotation.z = rotz;
	scene.add(text) 
}


// function onWindowResize() {
//
// camera.aspect = window.innerWidth / window.innerHeight;
// camera.updateProjectionMatrix();
// renderer.setSize( window.innerWidth, window.innerHeight );
//
// }

function onDocumentMouseMove( event ) {
	var arr=getRender3D();

	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    if (currAss[4]==event.target.parentNode) {
	    	event.preventDefault();
	    	mouse.x = ( (event.clientX-event.target.getBoundingClientRect().left) / currAss[15]) * 2 - 1;
	    	mouse.y = - ( (event.clientY-event.target.getBoundingClientRect().top) / currAss[16] ) * 2 + 1;
	    	break;
	    }
	}
}
function animate() {

	requestAnimationFrame( animate );

	var arr=getRender3D();

	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    currAss[13].update();
	}
	renderAll();
	
}

function renderAll() {
	var arr=getRender3D();

	for (i=0;i<arr.length;i++) {
	    var currAss = arr[i];
	    render(currAss);
	}
}

function render(scene3d) {
	var scene = scene3d[1];
	var camera = scene3d[2];
	var raycaster = scene3d[3];
	var container = scene3d[4];
	var renderer = scene3d[5];
	var vectorX = scene3d[6];
	var vectorY = scene3d[7];
	
	if (scene3d[14]!=null) {
		camera.lookAt(scene3d[14]);

		camera.updateMatrixWorld();
		scene3d[13].target.copy(scene3d[14]);
		scene3d[14]=null;
	}

	raycaster.setFromCamera( mouse, camera );

	var intersects = raycaster.intersectObjects( scene.children );

	if ( intersects.length > 0 ) {
		var msg;
		var j;
		for(j=0;j<intersects.length;j++) {
			if (!intersects[j].object.material) continue;
			if (intersects[j].object.material.emissive) continue;
			msg = push3DMessage(scene3d,intersects[j].object);
			if (msg==null) continue;
			if (scene3d[8]!=null) {
				scene3d[8].material.emissive.setHex( scene3d[10] );
				scene3d[8]=null;
			}
			if (scene3d[9]!=null) {
				scene3d[9].material.emissive.setHex( scene3d[11] );
				scene3d[9]=null;
			}
			scene3d[8]=vectorX[msg[5]];
			scene3d[9]=vectorY[msg[6]];
			scene3d[10]=vectorX[msg[5]].material.emissive.getHex();
			scene3d[11]=vectorY[msg[6]].material.emissive.getHex();
			vectorX[msg[5]].material.emissive.setHex( 0xff0000 );
			vectorY[msg[6]].material.emissive.setHex( 0xff0000 );
			
		}

	
			
			
	} else {
		if (scene3d[8]!=null) {
			scene3d[8].material.emissive.setHex( scene3d[10] );
			scene3d[8]=null;
		}
		if (scene3d[9]!=null) {
			scene3d[9].material.emissive.setHex( scene3d[11] );
			scene3d[9]=null;
		}
		
		if(scene3d[12]!=null) {
			scene.remove( scene3d[12] );
			scene3d[12]=null;
		}
		
	}

	renderer.render( scene, camera );

}

THREE.SpriteAlignment = {};
THREE.SpriteAlignment.topLeft = new THREE.Vector2( 1, -1 );
THREE.SpriteAlignment.topCenter = new THREE.Vector2( 0, -1 );
THREE.SpriteAlignment.topRight = new THREE.Vector2( -1, -1 );
THREE.SpriteAlignment.centerLeft = new THREE.Vector2( 1, 0 );
THREE.SpriteAlignment.center = new THREE.Vector2( 0, 0 );
THREE.SpriteAlignment.centerRight = new THREE.Vector2( -1, 0 );
THREE.SpriteAlignment.bottomLeft = new THREE.Vector2( 1, 1 );
THREE.SpriteAlignment.bottomCenter = new THREE.Vector2( 0, 1 );
THREE.SpriteAlignment.bottomRight = new THREE.Vector2( -1, 1 );



function makeTextSprite( message, parameters )
{
	if ( parameters === undefined ) parameters = {};
	
	var fontface = parameters.hasOwnProperty("fontface") ? 
		parameters["fontface"] : "Arial";
	
	var fontsize = parameters.hasOwnProperty("fontsize") ? 
		parameters["fontsize"] : 18;
	
	var borderThickness = parameters.hasOwnProperty("borderThickness") ? 
		parameters["borderThickness"] : 4;
	
	var borderColor = parameters.hasOwnProperty("borderColor") ?
		parameters["borderColor"] : { r:0, g:0, b:0, a:1.0 };
	
	var backgroundColor = parameters.hasOwnProperty("backgroundColor") ?
		parameters["backgroundColor"] : { r:255, g:255, b:255, a:1.0 };

	var spriteAlignment = THREE.SpriteAlignment.topLeft;
		
	var canvas = document.createElement('canvas');
	var context = canvas.getContext('2d');
	context.font = "Bold " + fontsize + "px " + fontface;
    
	// get size data (height depends only on font size)
	var metrics = context.measureText( message );
	var textWidth = metrics.width;
	
	// background color
	context.fillStyle   = "rgba(" + backgroundColor.r + "," + backgroundColor.g + ","
								  + backgroundColor.b + "," + backgroundColor.a + ")";
	// border color
	context.strokeStyle = "rgba(" + borderColor.r + "," + borderColor.g + ","
								  + borderColor.b + "," + borderColor.a + ")";

	context.lineWidth = borderThickness;
	roundRect(context, borderThickness/2, borderThickness/2, textWidth + borderThickness, fontsize * 1.4 + borderThickness, 6);
	// 1.4 is extra height factor for text below baseline: g,j,p,q.
	
	// text color
	context.fillStyle = "rgba(0, 0, 0, 1.0)";

	context.fillText( message, borderThickness, fontsize + borderThickness);
	
	// canvas contents will be used for a texture
	var texture = new THREE.Texture(canvas) 
	texture.needsUpdate = true;

	var spriteMaterial = new THREE.SpriteMaterial( 
		{ map: texture, useScreenCoordinates: false, alignment: spriteAlignment } );
	var sprite = new THREE.Sprite( spriteMaterial );
	sprite.scale.set(200,100,1.0);
	return sprite;	
}

// function for drawing rounded rectangles
function roundRect(ctx, x, y, w, h, r) 
{
    ctx.beginPath();
    ctx.moveTo(x+r, y);
    ctx.lineTo(x+w-r, y);
    ctx.quadraticCurveTo(x+w, y, x+w, y+r);
    ctx.lineTo(x+w, y+h-r);
    ctx.quadraticCurveTo(x+w, y+h, x+w-r, y+h);
    ctx.lineTo(x+r, y+h);
    ctx.quadraticCurveTo(x, y+h, x, y+h-r);
    ctx.lineTo(x, y+r);
    ctx.quadraticCurveTo(x, y, x+r, y);
    ctx.closePath();
    ctx.fill();
	ctx.stroke();   
}


var MiddleGauge = function(container, configuration) {
	var that = {};
	var config = {
		size						: 1,
		clipWidth					: 200,
		clipHeight					: 110,
		ringInset					: 20,
		ringWidth					: 20,
		incrementWidth				: false,
		
		pointerWidth				: 10,
		pointerTailLength			: 5,
		pointerHeadLengthPercent	: 0.9,
		
		minValue					: 0,
		maxValue					: 10,
		
		minAngle					: -90,
		maxAngle					: 90,
		
		transitionMs				: 750,
		
		majorTicks					: 5,
		labelFormat					: d3.format(',g'),
		labelInset					: 10,
		label                       : '',
		
		arcColorFn					: d3.interpolateHsl(d3.rgb('#e8e2ca'), d3.rgb('#3e6c0a'))
	};
	var range = undefined;
	var r = undefined;
	var pointerHeadLength = undefined;
	var value = 0;
	
    var gauge = d3.select(container);
    var zone = $(container).parent().width();
	var svg = undefined;
	var arc = undefined;
	var scale = undefined;
	var ticks = undefined;
	var tickData = undefined;
	var pointer = undefined;

	var donut = d3.layout.pie();
	
	function deg2rad(deg) {
		return deg * Math.PI / 180;
	}
	
	function newAngle(d) {
		var ratio = scale(d);
		var newAngle = config.minAngle + (ratio * range);
		return newAngle;
	}
	
	function configure(configuration) {
		var prop = undefined;
		for ( prop in configuration ) {
			config[prop] = configuration[prop];
		}
		var sizerel = parseInt(zone);
		var cant=config.size;
		var divisor = 1;
		if (cant>=2) divisor=2;
	    config.size = (sizerel-20)/divisor;
	    config.clipWidth = config.size;
	    config.clipHeight = (config.size/2)+30;
		range = config.maxAngle - config.minAngle;
		r = config.size / 2;
		pointerHeadLength = Math.round(r * config.pointerHeadLengthPercent);

		 $(container).parent().height((config.clipHeight*(cant==1?.7:1)) * ((cant/2)+((cant%2==0)?0:1)));
		// a linear scale that maps domain values to a percent from 0..1
		scale = d3.scale.linear()
			.range([0,1])
			.domain([config.minValue, config.maxValue]);
			
		ticks = scale.ticks(config.majorTicks);
		tickData = d3.range(config.majorTicks).map(function() {return 1/config.majorTicks;});
		
		arc = d3.svg.arc()
			.innerRadius(!config.incrementWidth?
					r -  config.ringWidth - config.ringInset :
					function(d, i) {
						return r - ( config.ringWidth * (d*(i+1)*5) ) - config.ringInset;
			})
			.outerRadius(r - config.ringInset)
			.startAngle(function(d, i) {
				var ratio = d * i;
				return deg2rad(config.minAngle + (ratio * range));
			})
			.endAngle(function(d, i) {
				var ratio = d * (i+1);
				return deg2rad(config.minAngle + (ratio * range));
			});
	}
	that.configure = configure;
	
	function centerTranslation() {
		return 'translate('+r +','+ r +')';
	}
	
	function isRendered() {
		return (svg !== undefined);
	}
	that.isRendered = isRendered;
	
	function render(newValue) {
		svg = d3.select(container)
			.append('svg:svg')
				.attr('class', 'gauge')
				.attr('width', config.clipWidth)
				.attr('height', config.clipHeight);
		
		var centerTx = centerTranslation();
		
		var arcs = svg.append('g')
				.attr('class', 'arc')
				.attr('transform', centerTx);
		
		arcs.selectAll('path')
				.data(tickData)
			.enter().append('path')
				.attr('fill', function(d, i) {
					return config.arcColorFn(d * i);
				})
				.attr('d', arc);
		
		var lg = svg.append('g')
				.attr('class', 'label')
				.attr('transform', centerTx);
		lg.selectAll('text')
				.data(ticks)
			.enter().append('text')
				.attr('transform', function(d) {
					var ratio = scale(d);
					var newAngle = config.minAngle + (ratio * range);
					return 'rotate(' +newAngle +') translate(0,' +(config.labelInset - r) +')';
				})
				.text(config.labelFormat);

		var lineData = [ [config.pointerWidth / 2, 0], 
						[0, -pointerHeadLength],
						[-(config.pointerWidth / 2), 0],
						[0, config.pointerTailLength],
						[config.pointerWidth / 2, 0] ];
		var pointerLine = d3.svg.line().interpolate('monotone');
		var pg = svg.append('g').data([lineData])
				.attr('class', 'pointer')
				.attr('transform', centerTx);
				
		pointer = pg.append('path')
			.attr('d', pointerLine/*function(d) { return pointerLine(d) +'Z';}*/ )
			.attr('transform', 'rotate(' +config.minAngle +')');
			
		if ( config.label!='')
		{
			var fontSize = config.label.length<10?Math.round(config.clipWidth / 9):
				(config.label.length<20?Math.round(config.clipWidth / 18):Math.round(config.clipWidth / 27));
			svg.append("svg:text")
						.attr("x", config.clipWidth/2)
						.attr("y", config.clipHeight / 2 + fontSize / 2)
						.attr("dy", fontSize / 2)
						.attr("text-anchor", "middle")
						.text(config.label)
						.style("font-size", fontSize + "px")
						.style("fill", "#333")
						.style("stroke-width", "0px");
		}

		update(newValue === undefined ? 0 : newValue);
	}
	that.render = render;
	
	function update(newValue, newConfiguration) {
		if ( newConfiguration  !== undefined) {
			configure(newConfiguration);
		}
		var ratio = scale(newValue);
		var newAngle = config.minAngle + (ratio * range);
		pointer.transition()
			.duration(config.transitionMs)
			.ease('elastic')
			.attr('transform', 'rotate(' +newAngle +')');
	}
	that.update = update;

	configure(configuration);
	
	return that;
};




!function(){
	var Donut3D={};
	
	function pieTop(d, rx, ry, ir ){
		if(d.endAngle - d.startAngle == 0 ) return "M 0 0";
		var sx = rx*Math.cos(d.startAngle),
			sy = ry*Math.sin(d.startAngle),
			ex = rx*Math.cos(d.endAngle),
			ey = ry*Math.sin(d.endAngle);
			
		var ret =[];
		ret.push("M",sx,sy,"A",rx,ry,"0",(d.endAngle-d.startAngle > Math.PI? 1: 0),"1",ex,ey,"L",ir*ex,ir*ey);
		ret.push("A",ir*rx,ir*ry,"0",(d.endAngle-d.startAngle > Math.PI? 1: 0), "0",ir*sx,ir*sy,"z");
		return ret.join(" ");
	}

	function pieOuter(d, rx, ry, h ){
		var startAngle = (d.startAngle > Math.PI ? Math.PI : d.startAngle);
		var endAngle = (d.endAngle > Math.PI ? Math.PI : d.endAngle);
		
		var sx = rx*Math.cos(startAngle),
			sy = ry*Math.sin(startAngle),
			ex = rx*Math.cos(endAngle),
			ey = ry*Math.sin(endAngle);
			
			var ret =[];
			ret.push("M",sx,h+sy,"A",rx,ry,"0 0 1",ex,h+ey,"L",ex,ey,"A",rx,ry,"0 0 0",sx,sy,"z");
			return ret.join(" ");
	}

	function pieInner(d, rx, ry, h, ir ){
		var startAngle = (d.startAngle < Math.PI ? Math.PI : d.startAngle);
		var endAngle = (d.endAngle < Math.PI ? Math.PI : d.endAngle);
		
		var sx = ir*rx*Math.cos(startAngle),
			sy = ir*ry*Math.sin(startAngle),
			ex = ir*rx*Math.cos(endAngle),
			ey = ir*ry*Math.sin(endAngle);

			var ret =[];
			ret.push("M",sx, sy,"A",ir*rx,ir*ry,"0 0 1",ex,ey, "L",ex,h+ey,"A",ir*rx, ir*ry,"0 0 0",sx,h+sy,"z");
			return ret.join(" ");
	}

	function getPercent(d){
		return (d.endAngle-d.startAngle > 0.2 ? 
				Math.round(1000*(d.endAngle-d.startAngle)/(Math.PI*2))/10+'%' : '');
	}	
//	function getPercent(d) {
//		  return (d.endAngle - d.startAngle > 0.2 ?
//		  d.data.label + ': ' + Math.round(1000 * (d.endAngle - d.startAngle) / (Math.PI * 2)) / 10 + '%' : '');}
	Donut3D.transition = function(id, data, rx, ry, h, ir){
		function arcTweenInner(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return pieInner(i(t), rx+0.5, ry+0.5, h, ir);  };
		}
		function arcTweenTop(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return pieTop(i(t), rx, ry, ir);  };
		}
		function arcTweenOuter(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return pieOuter(i(t), rx-.5, ry-.5, h);  };
		}
		function textTweenX(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return 0.6*rx*Math.cos(0.5*(i(t).startAngle+i(t).endAngle));  };
		}
		function textTweenY(a) {
		  var i = d3.interpolate(this._current, a);
		  this._current = i(0);
		  return function(t) { return 0.6*rx*Math.sin(0.5*(i(t).startAngle+i(t).endAngle));  };
		}
		
		var _data = d3.layout.pie().sort(null).value(function(d) {return d.value;})(data);
		
		d3.select("#"+id).selectAll(".innerSlice").data(_data)
			.transition().duration(750).attrTween("d", arcTweenInner); 
			
		d3.select("#"+id).selectAll(".topSlice").data(_data)
			.transition().duration(750).attrTween("d", arcTweenTop); 
			
		d3.select("#"+id).selectAll(".outerSlice").data(_data)
			.transition().duration(750).attrTween("d", arcTweenOuter); 	
			
		d3.select("#"+id).selectAll(".percent").data(_data).transition().duration(750)
			.attrTween("x",textTweenX).attrTween("y",textTweenY).text(getPercent); 

	}
	
	Donut3D.draw=function(id, data, x /*center x*/, y/*center y*/, 
			rx/*radius x*/, ry/*radius y*/, h/*height*/, ir/*inner radius*/){
	
		var _data = d3.layout.pie()
				.sort(null)
				.value(function(d) {return d.value;})(data);
		
		var slices = d3.select("#"+id).append("g").attr("transform", "translate(" + x + "," + y + ")")
			.attr("class", "slices");
			
		slices.selectAll(".innerSlice").data(_data).enter().append("path").attr("class", "innerSlice")
			.style("fill", function(d) { return d3.hsl(d.data.color).darker(0.7); })
			.attr("d",function(d){ return pieInner(d, rx+0.5,ry+0.5, h, ir);})
			.each(function(d){this._current=d;});
		
		slices.selectAll(".topSlice").data(_data).enter().append("path").attr("class", "topSlice")
			.style("fill", function(d) { return d.data.color; })
			.style("stroke", function(d) { return d.data.color; })
			.attr("d",function(d){ return pieTop(d, rx, ry, ir);})
			.each(function(d){this._current=d;});
		
		slices.selectAll(".outerSlice").data(_data).enter().append("path").attr("class", "outerSlice")
			.style("fill", function(d) { return d3.hsl(d.data.color).darker(0.7); })
			.attr("d",function(d){ return pieOuter(d, rx-.5,ry-.5, h);})
			.each(function(d){this._current=d;});

		slices.selectAll(".percent").data(_data).enter().append("text").attr("class", "percent")
			.attr("x",function(d){ return 0.6*rx*Math.cos(0.5*(d.startAngle+d.endAngle));})
			.attr("y",function(d){ return 0.6*ry*Math.sin(0.5*(d.startAngle+d.endAngle));})
			.text(getPercent).each(function(d){this._current=d;});		
		
	
	}
	
	this.Donut3D = Donut3D;
}();


/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////
var createPorcComp = function(container, porcJSON, configuration) {
	var     WIDTH = configuration.width,
	HEIGHT = configuration.height,
	RADIUS = Math.min(WIDTH, HEIGHT) / 2,
	MARGIN = 20,
	SPACING = 10,
	ORANGE_DK = "#ffbb33",
	ORANGE_LT = "#ff8800",
	PURPLE_DK = "#9933cc",
	PURPLE_LT = "#ba9bc9",
	GREEN_DK = "#669900",
	GREEN_LT = "#99cc00",
	GRAY_DK = "#777777",
	GRAY_LT = "#a9a9a9";



	var     porcOverview = porcJSON.overview,
	porcCity = Object.keys(porcOverview)[0],
	porcNeighborhood = Object.keys(porcOverview)[1],
	porcBreakdown = porcJSON.breakdown;


	//Generate overview statistics
	var porcPercentage = porcOverview[porcNeighborhood] / porcOverview[porcCity];
	var porcRadiusCity = RADIUS - 20;
	var porcRadiusNeighborhood = porcRadiusCity * Math.sqrt(porcPercentage);
	var porcCircleRadii = [porcRadiusCity, porcRadiusNeighborhood];

	
	//Generate breakdown statistics
	var     porcBreakdownArray = [],
	porcLegendData = [],
	other,                  // data only lists top 4, catch others
	total = 0;              // total of breakdown stats <= 100

	for (var key in porcBreakdown) {
		if (porcBreakdown.hasOwnProperty(key)) {
			total += porcBreakdown[key];
			porcBreakdownArray.push(porcBreakdown[key]);
			porcLegendData.push(key + " " + porcBreakdown[key] + "%");
		}
	}

	other = 100 - total;
	porcBreakdownArray.push(other);
	porcLegendData.push("other " + other + "%");
	
	
	//Choose colors from palette
	var porcPalette = [ORANGE_LT, ORANGE_DK];
	var porcColor = d3.scale.ordinal()
	.range(porcPalette);


	//Create overview graph
	var porcSVG = d3.select("#"+container).append("svg")
	.attr("class", "porcSVG")
	.attr("width", WIDTH)
	.attr("height", HEIGHT)
	.append("g")
	.attr("transform", "translate(" + (porcRadiusCity + MARGIN) + "," + HEIGHT / 2 + ")");
	
	var porcCircles = porcSVG.selectAll("circle")
	.data(porcCircleRadii)
	.enter().append("circle")
	.attr("cx", 0)
	.attr("cy", function(d, i) {return porcRadiusCity - d})
	.attr("fill", function(d, i) {return porcColor(i);})
	.attr("stroke", function(d, i) {return porcColor(i);})
	.attr("r", 0)
	.transition()
	.duration(500)
	.delay(function(d, i) {return i*250;})
	.attr("r", function(d) {return d;});
	
	porcSVG.append("text")
	.attr("class", "citySVG")
	.attr("y", - porcRadiusCity * 2/3)
	.attr("dy", ".3em")
	.text(porcCity);
	
	porcSVG.append("text")
	.attr("class", "percentageSVG")
	.attr("y", porcRadiusCity / 2)
	.attr("dy", "0.4em")
	.text(Math.floor(porcPercentage * 100) + "%");
	
	
	//Create blowup lines
	var porcLines = d3.select("#"+container).append("g")
	.attr("transform", "translate(" + (porcRadiusCity + MARGIN) + "," + HEIGHT / 2 + ")");
	
	porcLines.append("line")
	.transition()
	.duration(250)
	.delay(2250)
	.attr("x1", 0)
	.attr("y1", 0)
	.attr("x2", 2 * porcRadiusCity + SPACING)
	.attr("y2", -porcRadiusCity)
	.style("stroke", "#ffbb33");
	
	porcLines.append("line")
	.transition()
	.duration(250)
	.delay(2250)
	.attr("x1", 0)
	.attr("y1", 2 * porcRadiusNeighborhood)
	.attr("x2", 2 * porcRadiusCity + SPACING)
	.attr("y2", 2 * porcRadiusNeighborhood)
	.style("stroke", "#ffbb33");
	
	
	//Create breakdown graph
	var porcArc = d3.svg.arc()
	.outerRadius(porcRadiusCity)
	.innerRadius(0);
	
	var porcPie = d3.layout.pie()
	.sort(null)
	.value(function(d) {return d;});
	
	var porcPieChart = d3.select("#"+container).append("g")
	.attr("transform", "translate(" + (3 * porcRadiusCity+MARGIN+10) +
	        "," + HEIGHT / 2 + ")")
	.selectAll("porcArc")
	.data(porcPie(porcBreakdownArray));
	
	porcPieChart.enter().append("g")
	.attr("class", "porcArc")
	.append("path")
	.style("fill", "#ffbb33")
	.style("stroke", "#ffffff")
	.style("stroke-width", 2)
	.transition()
	.duration(500)
	.delay(function(d, i) {return i * 250 + 1000})
	.attr("d", porcArc);
	
	porcPieChart.append("text")
	.transition()
	.delay(2250)
	.attr("class", "regionSVG")
	.attr("y", porcRadiusCity + 2*SPACING)
	.text(porcNeighborhood);
	
	//Create breakdown legend
	var porcLegend = d3.select("#"+container).append("div")
	.attr("class", "legend");
	
	porcLegend.selectAll("div")
	.data(porcLegendData)
	.enter().append("div")
	.attr("class", "legendItem")
	.transition()
	.duration(500)
	.delay(function(d, i) {return i * 250 + 1000})
	.text(function(d) {return d});


}

function liquidFillGaugeDefaultSettings(){
    return {
        minValue: 0, // The gauge minimum value.
        maxValue: 100, // The gauge maximum value.
        circleThickness: 0.05, // The outer circle thickness as a percentage of it's radius.
        circleFillGap: 0.05, // The size of the gap between the outer circle and wave circle as a percentage of the outer circles radius.
        circleColor: "#178BCA", // The color of the outer circle.
        waveHeight: 0.05, // The wave height as a percentage of the radius of the wave circle.
        waveCount: 1, // The number of full waves per width of the wave circle.
        waveRiseTime: 1000, // The amount of time in milliseconds for the wave to rise from 0 to it's final height.
        waveAnimateTime: 18000, // The amount of time in milliseconds for a full wave to enter the wave circle.
        waveRise: true, // Control if the wave should rise from 0 to it's full height, or start at it's full height.
        waveHeightScaling: true, // Controls wave size scaling at low and high fill percentages. When true, wave height reaches it's maximum at 50% fill, and minimum at 0% and 100% fill. This helps to prevent the wave from making the wave circle from appear totally full or empty when near it's minimum or maximum fill.
        waveAnimate: true, // Controls if the wave scrolls or is static.
        waveColor: "#178BCA", // The color of the fill wave.
        waveOffset: 0, // The amount to initially offset the wave. 0 = no offset. 1 = offset of one full wave.
        textVertPosition: .5, // The height at which to display the percentage text withing the wave circle. 0 = bottom, 1 = top.
        textSize: 1, // The relative height of the text to display in the wave circle. 1 = 50%
        valueCountUp: true, // If true, the displayed value counts up from 0 to it's final value upon loading. If false, the final value is displayed.
        displayPercent: true, // If true, a % symbol is displayed after the value.
        textColor: "#045681", // The color of the value text when the wave does not overlap it.
        waveTextColor: "#A4DBf8" // The color of the value text when the wave overlaps it.
    };
}

function loadLiquidFillGauge(elementId, dataset, value, config) {
    if(config == null) config = liquidFillGaugeDefaultSettings();
    value=value%100;
    var gauge = d3.select("#" + elementId);
    var radius = Math.min(parseInt(gauge.style("width")), parseInt(gauge.style("height")))/2;
    var locationX = parseInt(gauge.style("width"))/2 - radius;
    var locationY = parseInt(gauge.style("height"))/2 - radius;
    var fillPercent = Math.max(config.minValue, Math.min(config.maxValue, value))/config.maxValue;

    var waveHeightScale;
    if(config.waveHeightScaling){
        waveHeightScale = d3.scale.linear()
            .range([0,config.waveHeight,0])
            .domain([0,50,100]);
    } else {
        waveHeightScale = d3.scale.linear()
            .range([config.waveHeight,config.waveHeight])
            .domain([0,100]);
    }

    var textPixels = (config.textSize*radius/2);
    var textFinalValue = parseFloat(value).toFixed(2);
    var textStartValue = config.valueCountUp?config.minValue:textFinalValue;
    var percentText = config.displayPercent?"%":"";
    var circleThickness = config.circleThickness * radius;
    var circleFillGap = config.circleFillGap * radius;
    var fillCircleMargin = circleThickness + circleFillGap;
    var fillCircleRadius = radius - fillCircleMargin;
    var waveHeight = fillCircleRadius*waveHeightScale(fillPercent*100);

    var waveLength = fillCircleRadius*2/config.waveCount;
    var waveClipCount = 1+config.waveCount;
    var waveClipWidth = waveLength*waveClipCount;

    // Rounding functions so that the correct number of decimal places is always displayed as the value counts up.
    var textRounder = function(value){ return Math.round(value); };
    if(parseFloat(textFinalValue) != parseFloat(textRounder(textFinalValue))){
        textRounder = function(value){ return parseFloat(value).toFixed(1); };
    }
    if(parseFloat(textFinalValue) != parseFloat(textRounder(textFinalValue))){
        textRounder = function(value){ return parseFloat(value).toFixed(2); };
    }

    // Data for building the clip wave area.
    var data = [];
    for(var i = 0; i <= 40*waveClipCount; i++){
        data.push({x: i/(40*waveClipCount), y: (i/(40))});
    }

    // Scales for drawing the outer circle.
    var gaugeCircleX = d3.scale.linear().range([0,2*Math.PI]).domain([0,1]);
    var gaugeCircleY = d3.scale.linear().range([0,radius]).domain([0,radius]);

    // Scales for controlling the size of the clipping path.
    var waveScaleX = d3.scale.linear().range([0,waveClipWidth]).domain([0,1]);
    var waveScaleY = d3.scale.linear().range([0,waveHeight]).domain([0,1]);

    // Scales for controlling the position of the clipping path.
    var waveRiseScale = d3.scale.linear()
        // The clipping area size is the height of the fill circle + the wave height, so we position the clip wave
        // such that the it will overlap the fill circle at all when at 0%, and will totally cover the fill
        // circle at 100%.
        .range([(fillCircleMargin+fillCircleRadius*2+waveHeight),(fillCircleMargin-waveHeight)])
        .domain([0,1]);
    var waveAnimateScale = d3.scale.linear()
        .range([0, waveClipWidth-fillCircleRadius*2]) // Push the clip area one full wave then snap back.
        .domain([0,1]);

    // Scale for controlling the position of the text within the gauge.
    var textRiseScaleY = d3.scale.linear()
        .range([fillCircleMargin+fillCircleRadius*2,(fillCircleMargin+textPixels*0.7)])
        .domain([0,1]);

    // Center the gauge within the parent SVG.
    var gaugeGroup = gauge.append("g")
        .attr('transform','translate('+locationX+','+locationY+')');

    // Draw the outer circle.
    var gaugeCircleArc = d3.svg.arc()
        .startAngle(gaugeCircleX(0))
        .endAngle(gaugeCircleX(1))
        .outerRadius(gaugeCircleY(radius))
        .innerRadius(gaugeCircleY(radius-circleThickness));
    gaugeGroup.append("path")
        .attr("d", gaugeCircleArc)
        .style("fill", config.circleColor)
        .attr('transform','translate('+radius+','+radius+')');

    // Text where the wave does not overlap.
    var text1 = gaugeGroup.append("text")
        .text(textRounder(textStartValue) + percentText)
        .attr("class", "liquidFillGaugeText")
        .attr("text-anchor", "middle")
        .attr("font-size", textPixels + "px")
        .style("fill", config.textColor)
        .attr('transform','translate('+radius+','+textRiseScaleY(config.textVertPosition)+')');

    if (dataset!='') {
        var textL = gaugeGroup.append("text")
        .text(dataset)
        .attr("class", "liquidFillGaugeText")
        .attr("text-anchor", "middle")
        .attr("font-size", (textPixels/2) + "px")
        .style("fill", config.textColor)
        .attr('transform','translate('+radius+','+(textRiseScaleY(config.textVertPosition)+(textPixels/2))+')');

    }
    
    // The clipping wave area.
    var clipArea = d3.svg.area()
        .x(function(d) { return waveScaleX(d.x); } )
        .y0(function(d) { return waveScaleY(Math.sin(Math.PI*2*config.waveOffset*-1 + Math.PI*2*(1-config.waveCount) + d.y*2*Math.PI));} )
        .y1(function(d) { return (fillCircleRadius*2 + waveHeight); } );
    var waveGroup = gaugeGroup.append("defs")
        .append("clipPath")
        .attr("id", "clipWave" + elementId);
    var wave = waveGroup.append("path")
        .datum(data)
        .attr("d", clipArea)
        .attr("T", 0);

    // The inner circle with the clipping wave attached.
    var fillCircleGroup = gaugeGroup.append("g")
        .attr("clip-path", "url(#clipWave" + elementId + ")");
    fillCircleGroup.append("circle")
        .attr("cx", radius)
        .attr("cy", radius)
        .attr("r", fillCircleRadius)
        .style("fill", config.waveColor);

    // Text where the wave does overlap.
    var text2 = fillCircleGroup.append("text")
        .text(textRounder(textStartValue) + percentText)
        .attr("class", "liquidFillGaugeText")
        .attr("text-anchor", "middle")
        .attr("font-size", textPixels + "px")
        .style("fill", config.waveTextColor)
        .attr('transform','translate('+radius+','+textRiseScaleY(config.textVertPosition)+')');

    if (dataset!='') {
        var textL2 = fillCircleGroup.append("text")
        .text(dataset)
        .attr("class", "liquidFillGaugeText")
        .attr("text-anchor", "middle")
        .attr("font-size", (textPixels/2) + "px")
        .style("fill", config.waveTextColor)
        .attr('transform','translate('+radius+','+(textRiseScaleY(config.textVertPosition)+(textPixels/2))+')');

    }
    
    // Make the value count up.
    if(config.valueCountUp){
        var textTween = function(){
            var i = d3.interpolate(this.textContent, textFinalValue);
            return function(t) { this.textContent = textRounder(i(t)) + percentText; }
        };
        text1.transition()
            .duration(config.waveRiseTime)
            .tween("text", textTween);
        text2.transition()
            .duration(config.waveRiseTime)
            .tween("text", textTween);
    }

    // Make the wave rise. wave and waveGroup are separate so that horizontal and vertical movement can be controlled independently.
    var waveGroupXPosition = fillCircleMargin+fillCircleRadius*2-waveClipWidth;
    if(config.waveRise){
        waveGroup.attr('transform','translate('+waveGroupXPosition+','+waveRiseScale(0)+')')
            .transition()
            .duration(config.waveRiseTime)
            .attr('transform','translate('+waveGroupXPosition+','+waveRiseScale(fillPercent)+')')
            .each("start", function(){ wave.attr('transform','translate(1,0)'); }); // This transform is necessary to get the clip wave positioned correctly when waveRise=true and waveAnimate=false. The wave will not position correctly without this, but it's not clear why this is actually necessary.
    } else {
        waveGroup.attr('transform','translate('+waveGroupXPosition+','+waveRiseScale(fillPercent)+')');
    }

    if(config.waveAnimate) animateWave();

    function animateWave() {
        wave.attr('transform','translate('+waveAnimateScale(wave.attr('T'))+',0)');
        wave.transition()
            .duration(config.waveAnimateTime * (1-wave.attr('T')))
            .ease('linear')
            .attr('transform','translate('+waveAnimateScale(1)+',0)')
            .attr('T', 1)
            .each('end', function(){
                wave.attr('T', 0);
                animateWave(config.waveAnimateTime);
            });
    }

    function GaugeUpdater(){
        this.update = function(value){
            var newFinalValue = parseFloat(value).toFixed(2);
            var textRounderUpdater = function(value){ return Math.round(value); };
            if(parseFloat(newFinalValue) != parseFloat(textRounderUpdater(newFinalValue))){
                textRounderUpdater = function(value){ return parseFloat(value).toFixed(1); };
            }
            if(parseFloat(newFinalValue) != parseFloat(textRounderUpdater(newFinalValue))){
                textRounderUpdater = function(value){ return parseFloat(value).toFixed(2); };
            }

            var textTween = function(){
                var i = d3.interpolate(this.textContent, parseFloat(value).toFixed(2));
                return function(t) { this.textContent = textRounderUpdater(i(t)) + percentText; }
            };

            text1.transition()
                .duration(config.waveRiseTime)
                .tween("text", textTween);
            text2.transition()
                .duration(config.waveRiseTime)
                .tween("text", textTween);

            var fillPercent = Math.max(config.minValue, Math.min(config.maxValue, value))/config.maxValue;
            var waveHeight = fillCircleRadius*waveHeightScale(fillPercent*100);
            var waveRiseScale = d3.scale.linear()
                // The clipping area size is the height of the fill circle + the wave height, so we position the clip wave
                // such that the it will overlap the fill circle at all when at 0%, and will totally cover the fill
                // circle at 100%.
                .range([(fillCircleMargin+fillCircleRadius*2+waveHeight),(fillCircleMargin-waveHeight)])
                .domain([0,1]);
            var newHeight = waveRiseScale(fillPercent);
            var waveScaleX = d3.scale.linear().range([0,waveClipWidth]).domain([0,1]);
            var waveScaleY = d3.scale.linear().range([0,waveHeight]).domain([0,1]);
            var newClipArea;
            if(config.waveHeightScaling){
                newClipArea = d3.svg.area()
                    .x(function(d) { return waveScaleX(d.x); } )
                    .y0(function(d) { return waveScaleY(Math.sin(Math.PI*2*config.waveOffset*-1 + Math.PI*2*(1-config.waveCount) + d.y*2*Math.PI));} )
                    .y1(function(d) { return (fillCircleRadius*2 + waveHeight); } );
            } else {
                newClipArea = clipArea;
            }

            var newWavePosition = config.waveAnimate?waveAnimateScale(1):0;
            wave.transition()
                .duration(0)
                .transition()
                .duration(config.waveAnimate?(config.waveAnimateTime * (1-wave.attr('T'))):(config.waveRiseTime))
                .ease('linear')
                .attr('d', newClipArea)
                .attr('transform','translate('+newWavePosition+',0)')
                .attr('T','1')
                .each("end", function(){
                    if(config.waveAnimate){
                        wave.attr('transform','translate('+waveAnimateScale(0)+',0)');
                        animateWave(config.waveAnimateTime);
                    }
                });
            waveGroup.transition()
                .duration(config.waveRiseTime)
                .attr('transform','translate('+waveGroupXPosition+','+newHeight+')')
        }
    }

    return new GaugeUpdater();
}

function addGlobeArc(place,w,h,data,link) {
	GlobeArc.initializeGlobeArc(place,w,h,data,link);
}

!function(){
	var GlobeArc={};
	var proj ;
	var sky;
	var path;
	var swoosh;
	var links = [];
	var arcLines = [];
	var svg
	
	function geoJSON( data ) {
	
		// Convert JSON data to GeoJSON:
		var _geoJSON = {
				'type': 'FeatureCollection',
				'features': [ ]
			};
		
		for (var i = 0; i < data.length; i++) {
			_geoJSON.features.push( {
				'type': 'Feature',
				'geometry': {
					'type': 'Point',
					'coordinates': [data[i].longitude, data[i].latitude] // [x,y]
				}, 
				'properties': {
					'name': data[i]['returned address']
				}
			});
		
		}; // end FOR i
		
		return _geoJSON;
	
	}; // end FUNCTION geoJSON(data)
	function ready(error, world) {
		 var places=arcLines;
		  var ocean_fill = svg.append("defs").append("radialGradient")
	        .attr("id", "ocean_fill")
	        .attr("cx", "75%")
	        .attr("cy", "25%");
	      ocean_fill.append("stop").attr("offset", "5%").attr("stop-color", "#fff");
	      ocean_fill.append("stop").attr("offset", "100%").attr("stop-color", "#ababab");
	
	  var globe_highlight = svg.append("defs").append("radialGradient")
	        .attr("id", "globe_highlight")
	        .attr("cx", "75%")
	        .attr("cy", "25%");
	      globe_highlight.append("stop")
	        .attr("offset", "5%").attr("stop-color", "#ffd")
	        .attr("stop-opacity","0.6");
	      globe_highlight.append("stop")
	        .attr("offset", "100%").attr("stop-color", "#ba9")
	        .attr("stop-opacity","0.2");
	
	  var globe_shading = svg.append("defs").append("radialGradient")
	        .attr("id", "globe_shading")
	        .attr("cx", "55%")
	        .attr("cy", "45%");
	      globe_shading.append("stop")
	        .attr("offset","30%").attr("stop-color", "#fff")
	        .attr("stop-opacity","0")
	      globe_shading.append("stop")
	        .attr("offset","100%").attr("stop-color", "#505962")
	        .attr("stop-opacity","0.3")
	
	  var drop_shadow = svg.append("defs").append("radialGradient")
	        .attr("id", "drop_shadow")
	        .attr("cx", "50%")
	        .attr("cy", "50%");
	      drop_shadow.append("stop")
	        .attr("offset","20%").attr("stop-color", "#000")
	        .attr("stop-opacity",".5")
	      drop_shadow.append("stop")
	        .attr("offset","100%").attr("stop-color", "#000")
	        .attr("stop-opacity","0")  
	
	  svg.append("ellipse")
	    .attr("cx", 440).attr("cy", 450)
	    .attr("rx", proj.scale()*.90)
	    .attr("ry", proj.scale()*.25)
	    .attr("class", "noclicks")
	    .style("fill", "url(#drop_shadow)");
	
	  svg.append("circle")
	    .attr("cx", width / 2).attr("cy", height / 2)
	    .attr("r", proj.scale())
	    .attr("class", "noclicks")
	    .style("fill", "url(#ocean_fill)");
	  
	  svg.append("path")
	    .datum(topojson.object(world, world.objects.land))
	    .attr("class", "land noclicks")
	    .attr("d", path);
	
	  svg.append("circle")
	    .attr("cx", width / 2).attr("cy", height / 2)
	    .attr("r", proj.scale())
	    .attr("class","noclicks")
	    .style("fill", "url(#globe_highlight)");
	
	  svg.append("circle")
	    .attr("cx", width / 2).attr("cy", height / 2)
	    .attr("r", proj.scale())
	    .attr("class","noclicks")
	    .style("fill", "url(#globe_shading)");
	
	  svg.append("g").attr("class","points")
	      .selectAll("text").data(places.features)
	    .enter().append("path")
	      .attr("class", "point")
	      .attr("d", path);
	
	  // spawn links between cities as source/target coord pairs
	//	  places.features.forEach(function(a) {
	//	    places.features.forEach(function(b) {
	//	      if (a !== b) {
	//	        links.push({
	//	          source: a.geometry.coordinates,
	//	          target: b.geometry.coordinates
	//	        });
	//	      }
	//	    });
	//	  });
	
	  // build geoJSON features from links array
	//	  links.forEach(function(e,i,a) {
	//	    var feature =   { "type": "Feature", "geometry": { "type": "LineString", "coordinates": [e.source,e.target] }}
	//	    arcLines.push(feature)
	//	  })
	
	  svg.append("g").attr("class","arcs")
	    .selectAll("path").data(arcLines)
	    .enter().append("path")
	      .attr("class","arc")
	      .attr("d",path)
	
	  svg.append("g").attr("class","flyers")
	    .selectAll("path").data(links)
	    .enter().append("path")
	    .attr("class","flyer")
	    .attr("d", function(d) { return swoosh(flying_arc(d)) })
	
	  refresh();
	}
	
	function flying_arc(pts) {
	  var source = pts.source,
	      target = pts.target;
	
	  var mid = location_along_arc(source, target, .5);
	  var result = [ proj(source),
	                 sky(mid),
	                 proj(target) ]
	  return result;
	}
	
	
	
	function refresh() {
	  svg.selectAll(".land").attr("d", path);
	  svg.selectAll(".point").attr("d", path);
	  
	  svg.selectAll(".arc").attr("d", path)
	    .attr("opacity", function(d) {
	        return fade_at_edge(d)
	    })
	
	  svg.selectAll(".flyer")
	    .attr("d", function(d) { return swoosh(flying_arc(d)) })
	    .attr("opacity", function(d) {
	      return fade_at_edge(d)
	    }) 
	}
	
	function fade_at_edge(d) {
	  var centerPos = proj.invert([width/2,height/2]),
	      arc = d3.geo.greatArc(),
	      start, end;
	  // function is called on 2 different data structures..
	  if (d.source) {
	    start = d.source, 
	    end = d.target;  
	  }
	  else {
	    start = d.geometry.coordinates[0];
	    end = d.geometry.coordinates[1];
	  }
	  
	  var start_dist = 1.57 - arc.distance({source: start, target: centerPos}),
	      end_dist = 1.57 - arc.distance({source: end, target: centerPos});
	    
	  var fade = d3.scale.linear().domain([-.1,0]).range([0,.1]) 
	  var dist = start_dist < end_dist ? start_dist : end_dist; 
	
	  return fade(dist)
	}
	
	function location_along_arc(start, end, loc) {
	  var interpolator = d3.geo.interpolate(start,end);
	  return interpolator(loc)
	}
	
	// modified from http://bl.ocks.org/1392560
	var m0, o0;
	function mousedown() {
	  m0 = [d3.event.pageX, d3.event.pageY];
	  o0 = proj.rotate();
	  d3.event.preventDefault();
	}
	function mousemove() {
	  if (m0) {
	    var m1 = [d3.event.pageX, d3.event.pageY]
	      , o1 = [o0[0] + (m1[0] - m0[0]) / 6, o0[1] + (m0[1] - m1[1]) / 6];
	    o1[1] = o1[1] > 30  ? 30  :
	            o1[1] < -30 ? -30 :
	            o1[1];
	    proj.rotate(o1);
	    sky.rotate(o1);
	    refresh();
	  }
	}
	function mouseup() {
	  if (m0) {
	    mousemove();
	    m0 = null;
	  } 
	}


	GlobeArc.initializeGlobeArc = function (place,w,h,data,link) {
		links=link;
		arcLines = geoJSON(data);
		d3.select("#"+place)
	    .on("mousemove", mousemove)
		.on("mouseup", mouseup);
		
		width = Math.min(w, h);
		height = Math.min(w, h);
		
		 proj = d3.geo.orthographic()
		    .translate([width / 2, height / 2])
		    .clipAngle(90)
		    .scale(width>height?((height*160)/460):((width*160)/460));
		 
		 sky = d3.geo.orthographic()
		    .translate([width / 2, height / 2])
		    .clipAngle(90)
		 /* .scale((width*110)/460); */
		    .scale(width>height?((height*220)/460):((width*220)/460));
		
		 path = d3.geo.path().projection(proj).pointRadius(2);
		
		 swoosh = d3.svg.line()
		      .x(function(d) { return d[0] })
		      .y(function(d) { return d[1] })
		      .interpolate("cardinal")
		      .tension(.0);
		
		 
		 svg = d3.select("#"+place).append("svg")
		            .attr("width", width)
		            .attr("height", height)
		            .on("mousedown", mousedown);
		
		 queue()
			.defer(d3.json, "/"+urlPrefix+"/js/world.json")
		    .await(ready);
		
	}
	this.GlobeArc = GlobeArc;
}();
