DELIMITER $$
CREATE DEFINER=`root`@`localhost` FUNCTION `solicitud_obtenerTipoDeSolicitud`(`parametro1` BIGINT(20), `parametro2` BIGINT(20)) RETURNS int(10) unsigned
    NO SQL
begin declare tipoDeSolicitud INT default 0;

select
	ifnull(id_tip_solicitud,
	0)
into
	tipoDeSolicitud
from
	nocap_solicitud
where
	id_mantenimiento = parametro1
	and id_registro = parametro2;

return tipoDeSolicitud;
end$$
DELIMITER ;