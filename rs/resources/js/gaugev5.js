

function addRadialBar(elementId,lines, data) {

	var width = $("#"+elementId).parent().width();
    var arcSize = ((width*.9) / (lines))/2;
    var fontsize= arcSize*.7>20?20:arcSize*.7;
    var num = lines;
    var innerRadius = arcSize  ;            
    $("#"+elementId).parent().height(width);


    function render() {
        var svg = d3.select('#'+elementId).append('svg').attr('width', width).attr('height', width);

        var arcs = data.map(function (obj, i) {
            return d3.svg.arc().innerRadius(i * arcSize + innerRadius).outerRadius((i + 1) * arcSize - (width / 100) + innerRadius);
        });
        var arcsGrey = data.map(function (obj, i) {
            return d3.svg.arc().innerRadius(i * arcSize + (innerRadius + ((arcSize / 2) - 2))).outerRadius((i + 1) * arcSize - ((arcSize / 2)) + (innerRadius));
        });

        var pieData = data.map(function (obj, i) {
            return [
                {value: obj.value * 0.75, arc: arcs[i], object: obj},
                {value: (100 - obj.value) * 0.75, arc: arcsGrey[i], object: obj},
                {value: 100 * 0.25, arc: arcs[i], object: obj}];
        });

        var pie = d3.layout.pie().sort(null).value(function (d) {
            return d.value;
        });

        var g = svg.selectAll('g').data(pieData).enter()
            .append('g')
            .attr('transform', 'translate(' + width / 2 + ',' + width / 2 + ') rotate(180)');
        var gText = svg.selectAll('g.textClass').data([{}]).enter()
            .append('g')
            .classed('textClass', true)
            .attr('transform', 'translate(' + width / 2 + ',' + width / 2 + ') rotate(180)');


        g.selectAll('path').data(function (d) {
            return pie(d);
        }).enter().append('path')
            .attr('id', function (d, i) {
                if (i == 1) {
                    return "Text" + d.data.object.label
                }
            })
            .attr('d', function (d) {
                return d.data.arc(d);
            }).attr('fill', function (d, i) {
            return i == 0 ? d.data.object.color : i == 1 ? '#D3D3D3' : 'none';
        });

        svg.selectAll('g').each(function (d, index) {
            var el = d3.select(this);
            var path = el.selectAll('path').each(function (r, i) {
                if (i === 1) {
                    var centroid = r.data.arc.centroid({
                        startAngle: r.startAngle + 0.05,
                        endAngle: r.startAngle + 0.001 + 0.05
                    });
                    var lableObj = r.data.object;
                    g.append('text')
                        .attr('font-size', fontsize)
                        .attr('dominant-baseline', 'central')
                        .append("textPath")
                        .attr("textLength", function (d, i) {
                            return 0;
                        })
                        .attr("xlink:href", "#Text" + r.data.object.label)
                        .attr("startOffset",'5')
                        .attr("dy",  '-3em')
                        
                        .text( lableObj.value >80?'':lableObj.value + '%');
                }
                if (i === 0) {
                    var centroidText = r.data.arc.centroid({
                        startAngle: r.startAngle,
                        endAngle: r.startAngle
                    });
                    var lableObj = r.data.object;
                    gText.append('text')
                        .attr('font-size', fontsize)
                        .text(lableObj.label.length>16?lableObj.label.substring(0,14)+"...":lableObj.label)
                        .attr('transform', "translate(" + (centroidText[0] - ((1.5 * width) / 100)) + "," + (centroidText[1] + ") rotate(" + (180) + ")"))
                        .attr('dominant-baseline', 'central');
                }
            });
        });
    }


    render()

	 

}
function addSpiral(elementId,  values, isdate) {
	var width = $("#"+elementId).parent().width(),
    height = $("#"+elementId).height()<width?width: $("#"+elementId).height(),
    start = 0,
    end = 2.25,
    numSpirals = 3,
    margin = {top:15,bottom:15,left:15,right:15};
	  $("#"+elementId).parent().height(width);

  var theta = function(r) {
    return numSpirals * Math.PI * r;
  };

  // used to assign nodes color by group
  var color = d3v5.scaleOrdinal(paleta);

  var r = d3v5.min([width, height]) / 2 - 40;

  var radius = d3v5.scaleLinear()
    .domain([start, end])
    .range([40, r]);

  var svg = d3v5.select("#"+elementId).append("svg")
    .attr("width", width + margin.right + margin.left)
    .attr("height", height + margin.left + margin.right)
    .append("g")
    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");

  var points = d3v5.range(start, end + 0.001, (end - start) / 1000);

  var spiral = d3v5.radialLine()
    .curve(d3v5.curveCardinal)
    .angle(theta)
    .radius(radius);

  var path = svg.append("path")
    .datum(points)
    .attr("id", "spiral")
    .attr("d", spiral)
    .style("fill", "none")
    .style("stroke", "steelblue");

  var spiralLength = path.node().getTotalLength(),
      N = 365,
      barWidth = (spiralLength / N) - 1;
  var someData = values;
//  for (var i = 0; i < N; i++) {
//    var currentDate = new Date();
//    currentDate.setDate(currentDate.getDate() + i);
//    someData.push({
//      date: currentDate,
//      value: Math.random(),
//      group: currentDate.getMonth()
//    });
//  }
  var timeScale;
  if (!isdate) {
	  timeScale = d3v5.scaleLinear()
	    .domain(d3v5.extent(someData, function(d){
	      return new Date(d.date);
	    }))
	    .range([0, spiralLength]);
	  
  } else {
	  timeScale = d3v5.scaleTime()
	    .domain(d3v5.extent(someData, function(d){
	      return d.date;
	    }))
	    .range([0, spiralLength]);
  }
  
  // yScale for the bar height
  var yScale = d3v5.scaleLinear()
    .domain([0, d3v5.max(someData, function(d){
      return d.value;
    })])
    .range([0, (r / numSpirals) - 30]);

  svg.selectAll("rect")
    .data(someData)
    .enter()
    .append("rect")
    .attr("x", function(d,i){
      
      var linePer = timeScale(d.date),
          posOnLine = path.node().getPointAtLength(linePer),
          angleOnLine = path.node().getPointAtLength(linePer - barWidth);
    
      d.linePer = linePer; // % distance are on the spiral
      d.x = posOnLine.x; // x postion on the spiral
      d.y = posOnLine.y; // y position on the spiral
      
      d.a = (Math.atan2(angleOnLine.y, angleOnLine.x) * 180 / Math.PI) - 90; // angle
																				// at
																				// the
																				// spiral
																				// position

      return d.x;
    })
    .attr("y", function(d){
      return d.y;
    })
    .attr("width", function(d){
      return barWidth;
    })
    .attr("height", function(d){
      return yScale(d.value);
    })
    .style("fill", function(d){return color(d.group);})
    .style("stroke", "none")
    .attr("transform", function(d){
      return "rotate(" + d.a + "," + d.x  + "," + d.y + ")"; // rotate the
																// bar
    });
  
  // add date labels
  var tF = d3v5.timeFormat("%b %Y"),
      firstInMonth = {};

  svg.selectAll("text")
    .data(someData)
    .enter()
    .append("text")
    .attr("dy", 10)
    .style("text-anchor", "start")
    .style("font", "10px arial")
    .append("textPath")
    // only add for the first of each month
    .filter(function(d){
      var sd = tF(d.date);
      if (!firstInMonth[sd]){
        firstInMonth[sd] = 1;
        return true;
      }
      return false;
    })
    .text(function(d){
      return tF(d.date);
    })
    // place text along spiral
    .attr("xlink:href", "#spiral")
    .style("fill", "grey")
    .attr("startOffset", function(d){
      return ((d.linePer / spiralLength) * 100) + "%";
    })


  var tooltip = d3v5.select("#"+elementId)
  .append('div')
  .attr('class', 'tooltip');

  tooltip.append('div')
  .attr('class', 'date');
  tooltip.append('div')
  .attr('class', 'value');

  svg.selectAll("rect")
  .on('mouseover', function(d) {

      tooltip.select('.date').html("Eje: <b>" + new Date(d.date).toDateString() + "</b>");
      tooltip.select('.value').html("Valor: <b>" + Math.round(d.value*100)/100 + "<b>");
      tooltip.select('.group').html("Grupo: <b>" +d.group + "<b>");

      d3v5.select(this)
      .style("fill","#FFFFFF")
      .style("stroke","#000000")
      .style("stroke-width","2px");

      tooltip.style('display', 'block');
      tooltip.style('opacity',2);

  })
  .on('mousemove', function(d) {
      tooltip.style('top', (d3v5.event.layerY + 10) + 'px')
      .style('left', (d3v5.event.layerX - 25) + 'px');
  })
  .on('mouseout', function(d) {
      d3v5.selectAll("rect")
      .style("fill", function(d){return color(d.group);})
      .style("stroke", "none")

      tooltip.style('display', 'none');
      tooltip.style('opacity',0);
  });	
}