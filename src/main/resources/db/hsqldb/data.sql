-----------------------------------------------
INSERT INTO TUTORES(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,fecha_solicitud,fecha_matriculacion)values
	('marrambla2','Believer123','88645678C','Maribel Ramos,','maribel.r@gmail.com','567825431','mi casa','2000-06-22','2015-06-22','2018-07-22');
INSERT INTO TUTORES(nick_usuario,contraseya,dni_usuario,fecha_solicitud,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,fecha_matriculacion)values
	('eveyugyug','Believer123','88645678J','20018-04-07','Evelyn Yugsi','ev.r@gmail.com','567825431','mi casa','2000-04-07','2018-07-22');

INSERT INTO cursos(curso_de_ingles) values ('A1');
INSERT INTO cursos(curso_de_ingles) values ('A2');
INSERT INTO cursos(curso_de_ingles) values ('B1');
INSERT INTO cursos(curso_de_ingles) values ('B2');
INSERT INTO cursos(curso_de_ingles) values ('C1');
INSERT INTO cursos(curso_de_ingles) values ('C2');
INSERT INTO cursos(curso_de_ingles) values ('APRENDIZAJELIBRE');

INSERT INTO grupos(nombre_grupo, cursos_curso_de_ingles) values ('grupo1', 'B1');
INSERT INTO grupos(nombre_grupo, cursos_curso_de_ingles) values ('grupo3', 'B2');


INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo,fecha_matriculacion) VALUES 
    ('Javi','Bebesita7','55635286F','Javi Martínez','martinez@gmail.com','626222111','Calle Junio de agosto','2000-06-23',0,'2012-12-11','marrambla2','grupo1','2020-2-7');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo,fecha_matriculacion) VALUES 
    ('antoniope','NahDeLocos88','55635286U','Antonio Pérez','jvii@gmail.com','622119555','Calle Junio de agosto','2000-06-23',1,'2012-12-11','marrambla2','grupo1','2020-2-7');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario) VALUES 
    ('patri25','NahDeLocos88','55635286S','Patricia Gómez','jvii@gmail.com','622119555','Calle Junio de agosto','2000-06-23',0,'2012-12-11','eveyugyug');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario) VALUES 
    ('natalia42','NahDeLocos88','55635286A','Natalia García','jvii@gmail.com','622119555','Calle Junio de agosto','2000-06-23',0,'2012-12-11','eveyugyug');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_matriculacion,tutores_nick_usuario,grupos_nombre_grupo,fecha_solicitud) VALUES 
    ('Gonzalo','NahDeLocos88','20502441B','Gonzalo Rodríguez','alumno@gmail.com','622119555','Calle Yucatan','1998-10-03',0,'2012-12-11','eveyugyug','grupo1','2020-11-26');

    
INSERT INTO profesores(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento) VALUES 
    ('MaribelJavi','NahDeLocos88','20505441B','Gonzalo','gonalvgar@gmail.com','622119555','Calle Yucatan','1998-10-03');

INSERT INTO walloffames(fechawall) VALUES ('2020-W50');
INSERT INTO walloffames(fechawall) VALUES ('2020-W51');
INSERT INTO walloffames(fechawall) VALUES ('2020-W45');
INSERT INTO walloffames(fechawall) VALUES ('2020-W40');
INSERT INTO walloffames(fechawall) VALUES ('2019-W12');
INSERT INTO walloffames(fechawall) VALUES ('2019-W20');
INSERT INTO premiados(descripcion,foto,alumnos_nick_usuario,walloffames_fechawall) VALUES ('He has done a very good job this week','Javi.jpg','Javi','2020-W50');
INSERT INTO premiados(descripcion,foto,alumnos_nick_usuario,walloffames_fechawall) VALUES ('He has done a great job','Gonzalo.jpg','Gonzalo','2020-W51');

INSERT INTO tipos_eventos(tipo) VALUES ('internal');
INSERT INTO tipos_eventos(tipo) VALUES ('external');

INSERT INTO tipos_pagos(tipo) VALUES ('bizum');
INSERT INTO tipos_pagos(tipo) VALUES ('efectivo');
INSERT INTO tipos_pagos(tipo) VALUES ('transferencia');

INSERT INTO eventos(title,descripcion,start,tipo_tipo,color) VALUES ('The Champions','Amazing','2021-01-23','internal','red');
INSERT INTO eventos(title,descripcion,start,end,tipo_tipo) VALUES ('HoolaHoop','Amzing play','2020-12-23','2020-12-25','external');
INSERT INTO eventos(title,descripcion,start,tipo_tipo,color) VALUES ('Fall Guys','Amazing wow','2021-01-15','internal','purple');

INSERT INTO inscripciones(fecha,registrado,evento_id,alumno_nick_usuario) VALUES ('2021-01-15','false','1','Javi');

INSERT INTO pagos(id,tipo,concepto,fecha,alumnos_nick_usuario) VALUES (1,'BIZUM', 'Pago matricula','2019-10-03','Javi');
INSERT INTO pagos(id,tipo,concepto,fecha,alumnos_nick_usuario) VALUES (2,'BIZUM', 'Pago matricula','2018-10-03','antoniope');
INSERT INTO pagos(id,tipo,concepto,fecha,alumnos_nick_usuario) VALUES (5,'BIZUM', 'Primer plazo','2019-10-04','Javi');

INSERT INTO asignaciones_profesor VALUES ('2019-10-03', 'MaribelJavi', 'grupo1');

INSERT INTO tipos_materiales VALUES ('Video');
INSERT INTO tipos_materiales VALUES ('Homework');

INSERT INTO materiales(id, fecha_subida, nombre_material, profesores_nick_usuario, tipo_material_tipo) values (1,'2021-02-03','Worbu.pdf','MaribelJavi','Homework');
INSERT INTO materiales(id, fecha_subida, nombre_material, profesores_nick_usuario, tipo_material_tipo) values (2,'2021-02-04','VERBS.pdf','MaribelJavi','Homework');

INSERT INTO feedbacks(id,comentario, completado, dia_entrega,valoracion, alumnos_nick_usuario,materiales_id) values(1,'A perfect way to learn','false',null,5,'Javi',1);
INSERT INTO feedbacks(id,comentario, completado, dia_entrega,valoracion, alumnos_nick_usuario,materiales_id) values(2,'A perfect way to learn','false',null,2,'Javi',2);
INSERT INTO feedbacks(id,comentario, completado, dia_entrega,valoracion, alumnos_nick_usuario,materiales_id) values(3,'A perfect way to learn','true','2020-10-04',1,'antoniope',2);




