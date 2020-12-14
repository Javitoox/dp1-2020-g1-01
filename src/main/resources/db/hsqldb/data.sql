-----------------------------------------------
INSERT INTO TUTORES(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,fecha_solicitud)values
	('marrambla2','believer','88645678C','Maribel Ramos,','maribel.r@gmail.com','567825431','mi casa','2000-06-22','2015-06-22');
INSERT INTO TUTORES(nick_usuario,contraseya,dni_usuario,fecha_solicitud,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento)values
	('eveyugyug','believer','88645678J','20018-04-07','Evelyn Yugsi','ev.r@gmail.com','567825431','mi casa','2000-04-07');

INSERT INTO cursos(curso_de_ingles) values ('A1');
INSERT INTO cursos(curso_de_ingles) values ('A2');
INSERT INTO cursos(curso_de_ingles) values ('B1');
INSERT INTO cursos(curso_de_ingles) values ('B2');
INSERT INTO cursos(curso_de_ingles) values ('C1');
INSERT INTO cursos(curso_de_ingles) values ('C2');
INSERT INTO cursos(curso_de_ingles) values ('APRENDIZAJELIBRE');

INSERT INTO grupos(nombre_grupo, cursos_curso_de_ingles) values ('grupo1', 'B1');
INSERT INTO grupos(nombre_grupo, cursos_curso_de_ingles) values ('grupo3', 'B2');


INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo) VALUES 
    ('Javi','zzz','55635286F','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','2000-06-23',1,'2012-12-11','marrambla2','grupo1');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo) VALUES 
    ('antoniope','bbb','55635286U','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','2000-06-23',1,'2012-12-11','marrambla2','grupo1');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo) VALUES 
    ('patri25','bbb','55635286S','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','2000-06-23',1,'2012-12-11','eveyugyug','grupo3');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_solicitud,tutores_nick_usuario,grupos_nombre_grupo) VALUES 
    ('natalia42','bbb','55635286A','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','2000-06-23',1,'2012-12-11','eveyugyug','grupo3');
INSERT INTO alumnos(nick_usuario,contraseya,dni_usuario,nombre_completo_usuario,correo_electronico_usuario,num_telefono_usuario,direccion_usuario,fecha_nacimiento,num_tareas_entregadas,fecha_matriculacion,tutores_nick_usuario,grupos_nombre_grupo,fecha_solicitud) VALUES 
    ('Gonzalo','aaa','20502441B','Gonzalo','gonalvgar.alumno@gmail.com','622119555','Calle Yucatan','1998-10-03',4,'2012-12-11','eveyugyug','grupo1','2020-11-26');


--INSERT ANTIGUOS

--Insercion de alumnos

--INSERT INTO profesores(nick_usuario) VALUES ('SergioSegura');
--
--INSERT INTO calendarios(id) VALUES(1);
--
--insert INTO cursos  VALUES ('B1');
--
--INSERT INTO grupos(nombre_grupo,curso_de_ingles) VALUES('grupo1', 'B1');
--
--INSERT INTO eventos(id_evento, nombre_evento, descripcion_evento,fecha_evento,id) VALUES(1,'Examen el 25/12/2020','El examen será tipo test','15/12/2020',1);
--
--INSERT INTO materiales(id,tipo_material,nombre_material,nick_usuario) VALUES (1,'VIDEO','Repaso AISS','SergioSegura');	
--
--INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nombre_grupo) VALUES(2,'12/04/2020','JaviV','grupo1');
--INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nombre_grupo) VALUES(5,'12/04/2020','Evelyn','grupo1');
--INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nombre_grupo) VALUES(3,'12/04/2020','Javi','grupo1');
--INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nombre_grupo) VALUES(3,'12/04/2020','Gonzalo','grupo1');
--
--INSERT INTO feedbacks(id_feedback,num_estrellas,comentario,nick_usuario,id) VALUES (1,'DOS','Muy buen vídeo','Evelyn',1);
--
--INSERT INTO solicitudes(fecha_solicitud,nick_usuario) VALUES('17/01/2020','JaviV');
--
----INSERT INTO wall_of_fames(id) VALUES(1);
----INSERT INTO premiados(descripcion,fecha_premiado,nick_usuario,id) VALUES('El mejor alumnno de la semana!','12/09/2020','JaviV',1);
--
--INSERT INTO pagos(concepto, tipo_pago, fecha, nick_usuario) VALUES ('Primera cuota','BIZUM','16/11/2020','JaviV');
--INSERT INTO solicitudes(fecha_solicitud, nick_usuario) VALUES ('11/01/2010', 'Evelyn');
--INSERT INTO solicitudes(fecha_solicitud, nick_usuario) VALUES ('11/01/2013', 'Javi');
--INSERT INTO solicitudes(fecha_solicitud, nick_usuario) VALUES ('11/01/2012', 'Gonzalo');

