

-----------------------------------------------

--Insercion de alumnos

INSERT INTO usuario VALUES 
	('JaviV','bbb','55635286F','Javie','jvii@gmail.com','955432565','Calle Junio de agosto','23/06/2000');
INSERT INTO usuario VALUES 
	('Evelyn','ccc','55635286P','EveY','evee@gmail.com','975422565','Calle Junio de agosto','24/06/2000');
INSERT INTO usuario VALUES 
    ('SergioSegura','bbb','55635286L','sergio','sg@gmail.com','955431565','Calle Junio de agosto','21/06/2000');

INSERT INTO profesor(nickusuario) VALUES ('SergioSegura');
			
INSERT INTO tutor(nicktutor,contraseñatutor,dnitutor,correoelectronicotutor,numtelefonotutor) VALUES('maribel22','aaaa','33454344R','mrb22@gmail.com','998876543');

INSERT INTO calendario(id) VALUES(1);

insert INTO curso  VALUES ('b2');

INSERT INTO grupo(nombregrupo,cursodeingles) VALUES('grupo1', 'b2');

INSERT INTO PROFESORGRUPO(nickusuario,nombregrupo)values('SergioSegura','grupo1');
INSERT INTO evento(idevento, nombreevento, descripcionevento,fechaevento,id) VALUES(1,'Examen el 25/12/2020','El examen será tipo test','15/12/2020',1);
INSERT INTO eventogrupo(idevento,nombregrupo) VALUES(1,'grupo1');

INSERT INTO material(id,tipomaterial,nombrematerial,nickusuario) VALUES (1,'VIDEO','Repaso AISS','SergioSegura');	
insert INTO cursomaterial(id,cursodeingles)VALUES (1,'b2');

INSERT INTO alumno(numtareasentregadas, fechamatriculacion, nickusuario,nicktutor,nombregrupo) VALUES(2,'12/04/2020','JaviV','maribel22','grupo1');
INSERT INTO alumno(numtareasentregadas, fechamatriculacion, nickusuario,nicktutor,nombregrupo) VALUES(5,'12/04/2020','Evelyn','maribel22','grupo1');

INSERT INTO materialalumno VALUES(1,'JaviV');

INSERT INTO feedback(idfeedback,numestrellas,comentario,nickusuario,id) VALUES (1,'DOS','Muy buen vídeo','Evelyn',1);

INSERT INTO solicitud(fechasolicitud,nickusuario) VALUES('17/01/2020','JaviV');

INSERT INTO walloffame(id) VALUES(1);
INSERT INTO premiado(descripcion,fechapremiado,nickusuario,id) VALUES('El mejor alumnno de la semana!','12/09/2020','JaviV',1);

INSERT INTO pago(concepto, tipopago, fecha, nickusuario) VALUES ('Primera cuota','BIZUM','16/11/2020','JaviV')
