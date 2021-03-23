
create definer = `sibod` @`localhost` procedure `dcap_bp`.`articuloDN_actualizarUbicacionArticulo`(in parmetero1 bigint(20), in parametro2, bigint(20))
update
	sibod_relacion_articulo_ubicacion_dn
set
	id_ubicacion = parametro2
where
id_articulo = parametro1