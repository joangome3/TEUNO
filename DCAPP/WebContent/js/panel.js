/*
 * Pagina: lineal.zul
 * 
 */

const width_threshold = 480;

$.get = function(key) {
	key = key.replace(/[\[]/, '\\[');
	key = key.replace(/[\]]/, '\\]');
	var pattern = "[\\?&]" + key + "=([^&#]*)";
	var regex = new RegExp(pattern);
	var url = unescape(window.location.href);
	var results = regex.exec(url);
	if (results === null) {
		return null;
	} else {
		return results[1];
	}
}

function drawLineChart(articulo) {
	if ($("#linealChart").length) {
		ctxLine = document.getElementById("linealChart").getContext("2d");
		optionsLine = {
			scales : {
				yAxes : [ {
					scaleLabel : {
						display : true,
						labelString : "Total"
					}
				} ]
			}
		};
		var lo_usuario = 2;
		var meses = new Array();
		var ingresos = new Array();
		graficosDWR
				.obtenerIngresosGraficoLineal(
						lo_usuario,
						articulo,
						function(data) {
							for (i = 0; i < data.length; i++) {
								var mes = String(data[i].mes);
								var ingreso = data[i].cantidad;
								meses.push(mes);
								ingresos.push(ingreso);
							}
							var egresos = new Array();
							graficosDWR
									.obtenerEgresosGraficoLineal(
											lo_usuario,
											articulo,
											function(data) {
												for (i = 0; i < data.length; i++) {													
													var egreso = data[i].cantidad;
													egresos.push(egreso);
												}
												optionsLine.maintainAspectRatio = $(
														window).width() < width_threshold ? false
														: true;

												configLine = {
													type : "line",
													data : {
														labels : meses,
														datasets : [
																{
																	label : "Total ingresos",
																	data : ingresos,
																	fill : false,
																	borderColor : "rgba(75, 192, 192, 1)",
																	lineTension : 0.1
																},
																{
																	label : "Total egresos",
																	data : egresos,
																	fill : false,
																	borderColor : "rgba(255, 174, 42, 1)",
																	lineTension : 0.1
																} ]
													},
													options : optionsLine
												};

												lineChart = new Chart(ctxLine,
														configLine);

											});
						});
	}
}

function updateChartOptions() {
	if ($(window).width() < width_threshold) {
		if (optionsLine) {
			optionsLine.maintainAspectRatio = false;
		}
		if (optionsBar) {
			optionsBar.maintainAspectRatio = false;
		}
		if (optionsPie) {
			optionsPie.maintainAspectRatio = false;
		}
	} else {
		if (optionsLine) {
			optionsLine.maintainAspectRatio = true;
		}
		if (optionsBar) {
			optionsBar.maintainAspectRatio = true;
		}
		if (optionsPie) {
			optionsPie.maintainAspectRatio = true;
		}
	}
}

//function drawBarChart() {
//	if ($("#barChart").length) {
//		ctxBar = document.getElementById("barChart").getContext("2d");
//		optionsBar = {
//			responsive : true,
//			scales : {
//				yAxes : [ {
//					ticks : {
//						beginAtZero : true
//					},
//					scaleLabel : {
//						display : true,
//						labelString : "Total"
//					}
//				} ]
//			}
//		};
//
//		var categorias = new Array();
//		var cantidades = new Array();
//		graficosDWR
//				.obtenerDatosGraficoDeBarras(
//						lo_usuario,
//						function(data) {
//							for (i = 0; i < data.length; i++) {
//								var nombre = String(data[i].nombre);
//								var cantidad = data[i].cantidad;
//								categorias.push(nombre);
//								cantidades.push(cantidad);
//							}
//							optionsBar.maintainAspectRatio = $(window).width() < width_threshold ? false
//									: true;
//
//							configBar = {
//								type : "bar",
//								data : {
//									labels : categorias,
//									datasets : [ {
//										label : "Total movimientos",
//										data : cantidades,
//										backgroundColor : [
//												"rgba(153, 102, 255, 0.7)",
//												"rgba(255,99,132,0.7)",
//												"rgba(255, 206, 86, 0.7)",
//												"rgba(75, 192, 192, 0.7)",
//												"rgba(153, 102, 255, 0.7)",
//												"rgba(255, 159, 64, 0.7)",
//												"rgba(116, 194, 218, 0.7)",
//												"rgba(13, 102, 255, 0.7)",
//												"rgba(255, 0, 132, 0.7)",
//												"rgba(255, 174, 42, 0.7)",
//												"rgba(5, 255, 255, 0.7)",
//												"rgba(5, 182, 17, 0.7)" ],
//										borderColor : [
//												"rgba(153, 102, 255, 1)",
//												"rgba(255, 99, 132, 1)",
//												"rgba(255, 206, 86, 1)",
//												"rgba(75, 192, 192, 1)",
//												"rgba(153, 102, 255, 1)",
//												"rgba(255, 159, 64, 1)",
//												"rgba(116, 194, 218, 1)",
//												"rgba(13, 102, 255, 1)",
//												"rgba(255, 0, 132, 1)",
//												"rgba(255, 174, 42, 1)",
//												"rgba(5, 255, 255, 1)",
//												"rgba(5, 182, 17, 1)" ],
//										borderWidth : 1
//									} ]
//								},
//								options : optionsBar
//							};
//
//							barChart = new Chart(ctxBar, configBar);
//
//						});
//	}
//}
//
//function drawPieChart() {
//	if ($("#pieChart").length) {
//		ctxPie = document.getElementById("pieChart").getContext("2d");
//		optionsPie = {
//			responsive : true,
//			maintainAspectRatio : false
//		};
//
//		var tipos = new Array();
//		var cantidades = new Array();
//		graficosDWR.obtenerDatosGraficoDePastel(lo_usuario, function(data) {
//			for (i = 0; i < data.length; i++) {
//				var tipo = String(data[i].nombre);
//				var cantidad = data[i].cantidad;
//				if (tipo == "I") {
//					tipos.push("Total ingresos");
//				} else if (tipo == "E") {
//					tipos.push("Total egresos");
//				} else if (tipo == undefined) {
//					tipos.push("-");
//				}
//				cantidades.push(cantidad);
//			}
//			configPie = {
//				type : "pie",
//				data : {
//					datasets : [ {
//						data : cantidades,
//						backgroundColor : [ "rgba(5, 182, 17, 0.7)",
//								"rgba(116, 194, 218, 0.7)" ],
//						borderColor : [ "rgba(5, 182, 17, 1)",
//								"rgba(116, 194, 218, 1)" ],
//						borderWidth : 1,
//						label : "Tipo de Movimientos"
//					} ],
//					labels : [ tipos[0], tipos[1] ]
//				},
//				options : optionsPie
//			};
//
//			pieChart = new Chart(ctxPie, configPie);
//
//		});
//	}
//}
//
//function ajaxCargarTopArticulosEnLista() {
//	graficosDWR.obtenerDatosTopArticulos(lo_usuario, function(data) {
//		for (i = 0; i < data.length; i++) {
//			document.getElementById("toparticulos").innerHTML = document
//					.getElementById("toparticulos").innerHTML
//					+ '<li class="tm-list-group-item">'
//					+ data[i].nombre
//					+ '</li>';
//		}
//	});
//}
//
//function ajaxCargarTopProveedoresEnLista() {
//	graficosDWR.obtenerDatosTopProveedores(lo_usuario, function(data) {
//		for (i = 0; i < data.length; i++) {
//			document.getElementById("topproveedores").innerHTML = document
//					.getElementById("topproveedores").innerHTML
//					+ '<li class="tm-list-group-item">'
//					+ data[i].nombre
//					+ '</li>';
//		}
//	});
//}
//
//function updateChartOptions() {
//	if ($(window).width() < width_threshold) {
//		if (optionsLine) {
//			optionsLine.maintainAspectRatio = false;
//		}
//		if (optionsBar) {
//			optionsBar.maintainAspectRatio = false;
//		}
//		if (optionsPie) {
//			optionsPie.maintainAspectRatio = false;
//		}
//	} else {
//		if (optionsLine) {
//			optionsLine.maintainAspectRatio = true;
//		}
//		if (optionsBar) {
//			optionsBar.maintainAspectRatio = true;
//		}
//		if (optionsPie) {
//			optionsPie.maintainAspectRatio = true;
//		}
//	}
//}
//
function updateLineChart() {
	if (lineChart) {
		lineChart.options = optionsLine;
		lineChart.update();
	}
}

//function updateBarChart() {
//	if (barChart) {
//		barChart.options = optionsBar;
//		barChart.update();
//	}
//}
//
//function updatePieChart() {
//	if (pieChart) {
//		pieChart.options = optionsPie;
//		pieChart.update();
//	}
//}

function reloadPage() {
	setTimeout(function() {
		window.location.reload();
	}); // Reload the page so that charts will display correctly
}

//function drawCalendar() {
//	if ($("#calendar").length) {
//		$("#calendar").fullCalendar({
//			height : 400,
//			events : [ {
//				title : "Meeting",
//				start : "2018-09-1",
//				end : "2018-09-2"
//			}, {
//				title : "Marketing trip",
//				start : "2018-09-6",
//				end : "2018-09-8"
//			}, {
//				title : "Follow up",
//				start : "2018-10-12"
//			}, {
//				title : "Team",
//				start : "2018-10-17"
//			}, {
//				title : "Company Trip",
//				start : "2018-10-25",
//				end : "2018-10-27"
//			}, {
//				title : "Review",
//				start : "2018-11-12"
//			}, {
//				title : "Plan",
//				start : "2018-11-18"
//			} ],
//			eventColor : "rgba(54, 162, 235, 0.4)"
//		});
//	}
//}
