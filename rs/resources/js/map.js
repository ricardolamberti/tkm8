 		var globalmap;
		var geocoder;
		var lista;
		var mapaComponente;
		var iconMapaDefa = null;
	    var mapOptions =null;
	    var marcas=null;
	    var points=null;
	    var direcciones=null;
	    var poligonos=null;
	    var circulos=null;
	    var arcos=null;
	    function cleanMapa () {
	    	direcciones=null;
	    	poligonos=null;
	    	circulos=null;
	    	marcas=null;
	    	arcos=null;
	    	points=null;
	    }
	    function addMarca (marca) {
	    	if (marcas==null) marcas = new Array();
	    	marcas[marcas.length]=marca;
	    }
	    function addPoints (point) {
	    	if (points==null) points = new Array();
	    	points[points.length]=point;
	    } 
	    function addDir (dir,name,icon) {
	    	if (direcciones==null) direcciones = new Array();
	    	ca=new Array();
	    	ca[0]=dir;
	    	ca[1]=name;
	    	ca[2]=icon;
	    	direcciones[direcciones.length]=ca;
	    }
	    function addPol (area, color1, opacity, ancho, color2, fill) {
	    	if (poligonos==null) poligonos = new Array();
	    	ca=new Array();
	    	ca[0]=area;
	    	ca[1]=color1;
	    	ca[2]=opacity;
	    	ca[3]=ancho;
	    	ca[4]=color2;
	    	ca[5]=fill;
	    	poligonos[poligonos.length]=ca;
	    }
	    function addCircle ( position, radius, color1, opacity, ancho, color2, fill, label) {
	    	if (circulos==null) circulos = new Array();
	    	ca=new Array();
	    	ca[0]=position;
	    	ca[1]=color1;
	    	ca[2]=opacity;
	    	ca[3]=ancho;
	    	ca[4]=color2;
	    	ca[5]=fill;
	    	ca[6]=radius;
	    	ca[7]=label;
	    	circulos[circulos.length]=ca;
	    }
	    function addArco ( positionFrom,positionTo, radius, color1, opacity, ancho, color2, fill, label) {
	    	if (arcos==null) arcos = new Array();
	    	ca=new Array();
	    	ca[0]=positionFrom;
	    	ca[1]=positionTo;
	    	ca[2]=color1;
	    	ca[3]=opacity;
	    	ca[4]=ancho;
	    	ca[5]=color2;
	    	ca[6]=fill;
	    	ca[7]=radius;
	    	ca[8]=label;
	    	arcos[arcos.length]=ca;
	    }
	    function setComponenteMapa(c) {
	    	mapaComponente=c;
	    }
	    function onloadMapa() {
			  var map = initializeMapa();
			  dibujarMapa(map);
			  globalmap = map;
	    }
	    function getMapOptions() {
	    	if (mapOptions!=null) return mapOptions;
	    	mapOptions = {
				    zoom: 8,
				    center: new google.maps.LatLng(-34, -58),
				    mapTypeId: google.maps.MapTypeId.ROADMAP
	 	    };

	    	return mapOptions;
	    }
	    function initializeMapa() {
			  var map = new google.maps.Map(document.getElementById(mapaComponente),  getMapOptions());
  	          globalmap= map;
			  return map;
	    }
	    function dibujarMapa(map) {
 			  lista = new Array();
			  geocoder = new google.maps.Geocoder();
			  if (direcciones) {
				  for (var i = 0; i < direcciones.length; i++) {	
						 if (direcciones[i][0].indexOf("GEO:")==-1) {
					     	setDireccion(getDireccion(direcciones[i][0]),direcciones[i][1],map,direcciones[i][2],getLittleDireccion(getDireccion(direcciones[i][0]))+" ("+direcciones[i][1]+")"); 
				     	} else {
				     		var options = { map: map, position: new google.maps.LatLng(getLat(direcciones[i][0]),getLng(direcciones[i][0])), content: beautify(getLittleDireccion(getDireccion(direcciones[i][0]))+" ("+direcciones[i][1]+")") };
							lista.push(options)
							
						    addPointMapa(map, new google.maps.LatLng(getLat(direcciones[i][0]),getLng(direcciones[i][0])) ,direcciones[i][2],getLittleDireccion(getDireccion(direcciones[i][0]))+" ("+direcciones[i][1]+")");
							//remove one zoom level to ensure no marker is on the edge.

				     	}
				   }
			  }
			  if (poligonos) {
					  for (var j = 0; j < poligonos.length; j++) {	
					   var polygon = new google.maps.Polygon({
								paths: poligonos[j][0],
								strokeColor: poligonos[j][1],
								strokeOpacity: poligonos[j][2],
								strokeWeight: poligonos[j][3],
								fillColor: poligonos[j][4],
								fillOpacity: poligonos[j][5]
							  });
							  polygon.setMap(map);
						  for (var k=0;k<poligonos[j][0].length;k++)
							  addPoints(poligonos[j][0][k]);
					  }
				  
		
			  }
			  if (circulos) {
				  for (var j = 0; j < circulos.length; j++) {	
						var cityCircle = new google.maps.Circle({
					        strokeColor: circulos[j][1],
					        strokeOpacity: circulos[j][2],
					        strokeWeight: circulos[j][3],
					        fillColor: circulos[j][4],
					        fillOpacity: circulos[j][5],
					        map: map,
					        center: circulos[j][0],
					        radius: circulos[j][6]
					      });
					    if (circulos[j][7]!=null && circulos[j][7]!='') {
	 					     addWindowLabel(map,cityCircle, circulos[j][0],circulos[j][7])
					    }

					  addPoints(circulos[j][0]);
				  }
			  
	
		  }
			  if (arcos) {
				  for (var j = 0; j < arcos.length; j++) {	
						   var cityArcos = new google.maps.Polygon({
								paths: [arcos[j][0],arcos[j][1]],
								strokeColor: arcos[j][2],
								strokeOpacity: arcos[j][3],
								strokeWeight: arcos[j][4],
								fillColor: arcos[j][5],
								fillOpacity: arcos[j][6]
							  });
						   cityArcos.setMap(map);
					    if (arcos[j][7]!=null && arcos[j][7]!='') {
	 					     addWindowLabel(map,cityArcos, arcos[j][0],arcos[j][8])
					    }

					  addPoints(arcos[j][0]);
					  addPoints(arcos[j][1]);
				  }
			  
	
		  }
	
		    

		  autocentrar(map);
		}
	    var activeInfoWindow = null;
		function addWindowLabel(map,marker,myLocation,label){
				
			
			// create an InfoWindow - for mouseover
			var infoWnd = new google.maps.InfoWindow();						
			// -----------------------
			// ON MOUSEOVER
			// -----------------------
			
			// add content to your InfoWindow
			infoWnd.setContent(label);
			
			// add listener on InfoWindow for mouseover event
			google.maps.event.addListener(marker, 'mouseover', function() {
				
				// Close active window if exists - [one might expect this to be default behaviour no?]				
				if(activeInfoWindow != null) activeInfoWindow.close();

				// Open new InfoWindow for mouseover event
				infoWnd.setPosition(myLocation);
				   	infoWnd.open(map, marker);
				
				// Store new open InfoWindow in global variable
				activeInfoWindow = infoWnd;				
			}); 							
			
			// on mouseout (moved mouse off marker) make infoWindow disappear
			google.maps.event.addListener(marker, 'mouseout', function() {
				infoWnd.close();	
			});
			
		}
	    function autocentrar(map) {
			var bounds = new google.maps.LatLngBounds();
	    	if (marcas) {
				  for (var i = 0; i < marcas.length; i++) {	
					  bounds.extend(marcas[i].getPosition());
				  }
			  }
	    	if (points) {
				  for (var i = 0; i < points.length; i++) {	
					  bounds.extend(points[i]);
				  }
	    	}
	    		map.setCenter(bounds.getCenter()); //or use custom center
				map.fitBounds(bounds);
	    	
			 map.setZoom(map.getZoom()); 

			// set a minimum zoom 
			// if you got only 1 marker or all markers are on the same address map will be zoomed too much.
			if(map.getZoom()> 15){
			  map.setZoom(15);
			}
	    }
	    function addPointMapa(mapa,location ,icono,titulo) {
	    	  // Add the marker at the clicked location, and add the next-available label
	    	  // from the array of alphabetical characters.
	    	  var marker = new google.maps.Marker({
	    	    position: location,
	    	    icon: icono,
	    	    title: titulo,
	    	    map: mapa
	    	  });
	    	  marker.setMap(mapa);
	    	  
	    	  addMarca(marker);
			return marker;
		}
	    function cleanMarcas() {
	    	 if (marcas) {
	    		 
			  for (var i = 0; i < marcas.length; i++) {	
	     		 marcas[i].setMap(null);
		      }
	    	 }
			  marcas=null;
			}

		function showEtiquetas() {
		  for (var i = 0; i < lista.length; i++) {	
     		 new google.maps.InfoWindow(lista[i]);
	      }
		}

		function getLat(direccion) {
			var posGeo = direccion.indexOf(" GEO:");
			var dirgeo = direccion.substring(posGeo+5);
			var pos = dirgeo.indexOf(",");
			return dirgeo.substring(0,pos);
		}
		function getLng(direccion) {
			var posGeo = direccion.indexOf(" GEO:");
			var dirgeo = direccion.substring(posGeo+5);
			var pos = dirgeo.indexOf(",");
			return dirgeo.substring(pos+1);
		}
		function getDireccion(direccion) {
			var posDir = direccion.indexOf("DIR:");
			var posGeo = direccion.indexOf(" GEO:");
			var dirgeo = "";
			if (posGeo==-1)
				dirgeo=posDir==-1?direccion:direccion.substring(posDir+4);
			else
				dirgeo=direccion.substring((posDir==-1?posDir:posDir+4),posGeo);
			return dirgeo;
		}
		function getLittleDireccion(direccion) {
			var posGeo = direccion.indexOf(",");
			var dirgeo = "";
			if (posGeo==-1)
				dirgeo=direccion.substring(0);
			else
				dirgeo=direccion.substring(0,posGeo);
			return dirgeo;
		}
		
//		var mapa = new Array();
		
		function setDireccion(direccion,detalle,map,icono,titulo) {
			var request = new Object(); //CREO UN OBJETO
			request.address = direccion; 
			if (direccion=="") return;
//			var pos = mapa.length;
//			mapa[pos]=new Array();
//			mapa[pos][0]=direccion;
//			mapa[pos][1]=detalle;
			geocoder.geocode(request, function(results, status) {
				addAddressToMap(results, status,map,titulo,icono);
			}); //geocode hace la conversi�n a un punto, y su segundo par�metro es una funci�n de callback
		}
		
//		function getMapa(name) {
//		  for (i=0;i<mapa.length;i++) {
//		    currAss = mapa[i];
//		    if (currAss!=null && getLittleDireccion(currAss[0]).toLowerCase()==getLittleDireccion(name).toLowerCase()) {
//		  	  return currAss[1];
//			}
//		  }
//		  return null;
//		}

		function addAddressToMap(response, status,map,titulo,icono) {
			if(!response) {
				
				return;    //si no pudo
			}
			if ( status != google.maps.GeocoderStatus.OK ) return;
    		
//			var resultado = getMapa(response[0].formatted_address );
			
 //    		var options = { map: map, position: response[0].geometry.location, content: beautify(response[0].formatted_address+" ("+resultado+")")  };
	   		var options = { map: globalmap, position: response[0].geometry.location, content: beautify(response[0].formatted_address)};
			lista.push(options)

			//icono que voy a usar para mostrar el punto en el mapa
		
			addPointMapa(map,  response[0].geometry.location ,icono,titulo);
			autocentrar(map);
		}
		
    	function handleError(errorFlag) {
				if (errorFlag) {
					var content = 'Error en el servicio de geolocalizacion.';
				} else {
					var content = 'Error: Tu navegador no soporta geolocalizaci�n.';
				}
				
				var options = { map: globalmap, position: new google.maps.LatLng(60, 105), content: content };
				var infowindow = new google.maps.InfoWindow(options);
				map.setCenter(options.position);
				
		}
	
       	function displayMap(componente) {
       		setComponenteMapa(componente);
       		google.maps.event.addDomListener(window, 'load', onloadMapa);
       	}
	
		function beautify(texto) {
			return '<font size="1">'+texto+'</font>';
		}