select a.id_relacion,
(select b.nom_tipo_servicio from sibod_tipo_servicio b where a.id_tipo_servicio = b.id_tipo_servicio) as servicio,
(select c.nom_tipo_tarea from sibod_tipo_tarea c where a.id_tipo_tarea = c.id_tipo_tarea) as tarea
from nocap_relacion_tipo_servicio_tipo_tarea a order by 1;