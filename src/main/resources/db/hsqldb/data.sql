

-----------------------------------------------

--Insercion de alumnos

INSERT INTO usuarios VALUES 
	('JaviV','bbb','55635286F','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','23/06/2000');
INSERT INTO usuarios VALUES 
	('Evelyn','ccc','55635286P','EveY','evee@gmail.com','975422565','Calle Junio de agosto','24/06/2000');
INSERT INTO usuarios VALUES 
	('Javi','zzz','35464786T','Javitoox','javito@gmail.com','635096785','Calle Pepe','13/08/2000');
INSERT INTO usuarios VALUES 
    ('SergioSegura','bbb','55635286L','sergio','sg@gmail.com','955431565','Calle Junio de agosto','21/06/2000');

INSERT INTO profesores(nick_usuario) VALUES ('SergioSegura');
			
INSERT INTO tutores(nick_tutor,contraseya_tutor,dni_tutor,correo_electronico_tutor,num_telefono_tutor) VALUES('maribel22','aaaa','33454344R','mrb22@gmail.com','998876543');

INSERT INTO calendarios(id) VALUES(1);

insert INTO cursos  VALUES ('b2');

INSERT INTO grupos(nombre_grupo,curso_de_ingles) VALUES('grupo1', 'b2');

INSERT INTO eventos(id_evento, nombre_evento, descripcion_evento,fecha_evento,id) VALUES(1,'Examen el 25/12/2020','El examen será tipo test','15/12/2020',1);

INSERT INTO evento_grupo(id_evento,nombre_grupo) VALUES(1,'grupo1');

INSERT INTO materiales(id,tipo_material,nombre_material,nick_usuario) VALUES (1,'VIDEO','Repaso AISS','SergioSegura');	

insert INTO curso_material(id,curso_de_ingles)VALUES (1,'b2');

INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nick_tutor,nombre_grupo) VALUES(2,'12/04/2020','JaviV','maribel22','grupo1');
INSERT INTO alumnos(num_tareas_entregadas, fecha_matriculacion, nick_usuario,nick_tutor,nombre_grupo) VALUES(5,'12/04/2020','Evelyn','maribel22','grupo1');

INSERT INTO material_alumno VALUES(1,'JaviV');

INSERT INTO profesor_grupo(nick_usuario,nombre_grupo)values('SergioSegura','grupo1');

INSERT INTO feedbacks(id_feedback,num_estrellas,comentario,nick_usuario,id) VALUES (1,'DOS','Muy buen vídeo','Evelyn',1);

INSERT INTO solicitudes(fecha_solicitud,nick_usuario) VALUES('17/01/2020','JaviV');

INSERT INTO wall_of_fames(id) VALUES(1);
INSERT INTO premiados(descripcion,fecha_premiado,nick_usuario,id) VALUES('El mejor alumnno de la semana!','12/09/2020','JaviV',1);

INSERT INTO pagos(concepto, tipo_pago, fecha, nick_usuario) VALUES ('Primera cuota','BIZUM','16/11/2020','JaviV')
