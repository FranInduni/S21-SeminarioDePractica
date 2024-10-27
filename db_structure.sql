/*
Alumno: Induni, Francisco Gustavo
Legajo: VINF014188
*/

DROP DATABASE IF EXISTS gestion_carrera_universitaria;

CREATE DATABASE gestion_carrera_universitaria CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci;

USE gestion_carrera_universitaria;

/*
**** TABLAS ****
*/
CREATE TABLE `gestion_carrera_universitaria`.`tblcarrera`  (
  `PK_Carrera_Id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `UQ_Carrera_Nombre` varchar(64) NOT NULL,
  `DF_Carrera_Modalidad` enum('MP','PH','ED','EDH') NOT NULL,
  `DF_Carrera_CreditosElectivas` int UNSIGNED NOT NULL,
  `DF_Carrera_CuposVerano` int UNSIGNED NOT NULL,
  `DF_Carrera_CuposNormal` int UNSIGNED NOT NULL,
  `DF_Carrera_CuposSoloEnB` int UNSIGNED NULL,
  `DF_Carrera_CuposIngresantes` int UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_Carrera_Id`),
  UNIQUE (`UQ_Carrera_Nombre`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblinstancia`  (
  `PK_Instancia_Id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_Instancia_Carrera` int UNSIGNED NOT NULL,
  `UQ_Instancia_Nombre` varchar(64) NOT NULL,
  `DF_Instancia_InscripcionEnV` bool NOT NULL,
  `DF_Instancia_InscripcionEn1A` bool NOT NULL,
  `DF_Instancia_InscripcionEn1B` bool NULL,
  `DF_Instancia_InscripcionEn2A`bool NOT NULL,
  `DF_Instancia_InscripcionEn2B` bool NULL,
  `DF_Instancia_InscripcionCuatrimestreMinimo` int UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_Instancia_Id`),
  UNIQUE (`FK_Instancia_Carrera`, `UQ_Instancia_Nombre`),
  CONSTRAINT `FK_Instancia_Carrera` FOREIGN KEY (`FK_Instancia_Carrera`) REFERENCES `gestion_carrera_universitaria`.`tblcarrera` (`PK_Carrera_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblcorrelativa`  (
  `PK_Correlativa_Instancia` int UNSIGNED NOT NULL,
  `PK_Correlativa_InstanciaCorrelativa` int UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_Correlativa_Instancia`, `PK_Correlativa_InstanciaCorrelativa`),
  CONSTRAINT `PK_Correlativa_Instancia` FOREIGN KEY (`PK_Correlativa_Instancia`) REFERENCES `gestion_carrera_universitaria`.`tblinstancia` (`PK_Instancia_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PK_Correlativa_InstanciaCorrelativa` FOREIGN KEY (`PK_Correlativa_InstanciaCorrelativa`) REFERENCES `gestion_carrera_universitaria`.`tblinstancia` (`PK_Instancia_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblmateria`  (
  `PK_Materia_Instancia` int UNSIGNED NOT NULL,
  `DF_Materia_Tipologia` enum('MEC','MIA','MIP','PROCESO','ELECTIVA') NOT NULL,
  `DF_Materia_CuatrimestreEnCarrera` int UNSIGNED NOT NULL,
  `DF_Materia_GastaCupo` bool NOT NULL,
  `DF_Materia_Creditos` int UNSIGNED NULL,
  PRIMARY KEY (`PK_Materia_Instancia`),
  CONSTRAINT `PK_Materia_Instancia` FOREIGN KEY (`PK_Materia_Instancia`) REFERENCES `gestion_carrera_universitaria`.`tblinstancia` (`PK_Instancia_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblperfil`  (
  `PK_Perfil_Id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `UQ_Perfil_Nombre` varchar(64) NOT NULL,
  PRIMARY KEY (`PK_Perfil_Id`),
  UNIQUE (`UQ_Perfil_Nombre`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblcarreraestudiante`  (
  `PK_CarreraEstudiante_Id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `FK_CarreraEstudiante_Perfil` int UNSIGNED NOT NULL,
  `FK_CarreraEstudiante_Carrera` int UNSIGNED NOT NULL,
  `DF_CarreraEstudiante_YearInicio` year NOT NULL,
  `DF_CarreraEstudiante_PeriodoInicio` enum('V','1A','1B','2A','2B') NOT NULL,
  PRIMARY KEY (`PK_CarreraEstudiante_Id`),
  UNIQUE (`FK_CarreraEstudiante_Perfil`, `FK_CarreraEstudiante_Carrera`),
  CONSTRAINT `FK_CarreraEstudiante_Perfil` FOREIGN KEY (`FK_CarreraEstudiante_Perfil`) REFERENCES `gestion_carrera_universitaria`.`tblperfil` (`PK_Perfil_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_CarreraEstudiante_Carrera` FOREIGN KEY (`FK_CarreraEstudiante_Carrera`) REFERENCES `gestion_carrera_universitaria`.`tblcarrera` (`PK_Carrera_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblcarreraestudianteconfiguracion`  (
  `PK_CarreraEstudianteConfiguracion_CarreraEstudiante` int UNSIGNED NOT NULL,
  `DF_CarreraEstudianteConfiguracion_CuposEnV` int UNSIGNED NOT NULL,
  `DF_CarreraEstudianteConfiguracion_CuposEn1A`int UNSIGNED NOT NULL,
  `DF_CarreraEstudianteConfiguracion_CuposEn1B`int UNSIGNED NULL,
  `DF_CarreraEstudianteConfiguracion_CuposEn2A`int UNSIGNED NOT NULL,
  `DF_CarreraEstudianteConfiguracion_CuposEn2B`int UNSIGNED NULL,
  `DF_CarreraEstudianteConfiguracion_CreditosExtra` int UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_CarreraEstudianteConfiguracion_CarreraEstudiante`),
  CONSTRAINT `PK_CarreraEstudianteConfiguracion_CarreraEstudiante` FOREIGN KEY (`PK_CarreraEstudianteConfiguracion_CarreraEstudiante`) REFERENCES `gestion_carrera_universitaria`.`tblcarreraestudiante` (`PK_CarreraEstudiante_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciainscripcion`  (
  `PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante` int UNSIGNED NOT NULL,
  `PK_CarreraEstudianteInstanciaInscripcion_Instancia` int UNSIGNED NOT NULL,
  `DF_CarreraEstudianteInstanciaInscripcion_Year` year NOT NULL,
  `DF_CarreraEstudianteInstanciaInscripcion_Periodo` enum('V','1A','1B','2A','2B') NOT NULL,
  PRIMARY KEY (`PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante`, `PK_CarreraEstudianteInstanciaInscripcion_Instancia`),
  CONSTRAINT `PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante` FOREIGN KEY (`PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante`) REFERENCES `gestion_carrera_universitaria`.`tblcarreraestudiante` (`PK_CarreraEstudiante_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PK_CarreraEstudianteInstanciaInscripcion_Instancia` FOREIGN KEY (`PK_CarreraEstudianteInstanciaInscripcion_Instancia`) REFERENCES `gestion_carrera_universitaria`.`tblinstancia` (`PK_Instancia_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;

CREATE TABLE `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciaaprobada`  (
  `PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante` int UNSIGNED NOT NULL,
  `PK_CarreraEstudianteInstanciaAprobada_Instancia` int UNSIGNED NOT NULL,
  PRIMARY KEY (`PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante`, `PK_CarreraEstudianteInstanciaAprobada_Instancia`),
  CONSTRAINT `PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante` FOREIGN KEY (`PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante`) REFERENCES `gestion_carrera_universitaria`.`tblcarreraestudiante` (`PK_CarreraEstudiante_Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `PK_CarreraEstudianteInstanciaAprobada_Instancia` FOREIGN KEY (`PK_CarreraEstudianteInstanciaAprobada_Instancia`) REFERENCES `gestion_carrera_universitaria`.`tblinstancia` (`PK_Instancia_Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_520_ci;



/*
**** FUNCIONES ****
*/
DELIMITER $$
CREATE DEFINER=CURRENT_USER FUNCTION `FUNCTION_CARRERA_INSTANCIA_LISTAR_CORRELATIVAS`(param_InstanciaId int UNSIGNED) RETURNS JSON
DETERMINISTIC
BEGIN
  DECLARE var_InstanciasCorrelativas JSON;

  SET var_InstanciasCorrelativas = JSON_ARRAY();

  SELECT COALESCE(JSON_ARRAYAGG(correlativas.PK_Correlativa_InstanciaCorrelativa), JSON_ARRAY())  INTO var_InstanciasCorrelativas
  FROM gestion_carrera_universitaria.tblcorrelativa AS correlativas
  WHERE correlativas.PK_Correlativa_Instancia = param_InstanciaId;

  RETURN var_InstanciasCorrelativas;
END;
$$
DELIMITER ;



/*
**** PROCEDIMIENTOS ****
*/
DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_PERFIL_CREAR`(
	IN param_Nombre varchar(64),
  OUT outparam_NewId int UNSIGNED
)
BEGIN
	INSERT INTO `gestion_carrera_universitaria`.`tblperfil` (UQ_Perfil_Nombre) VALUES (param_Nombre);
  SET outparam_NewId = LAST_INSERT_ID();
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_PERFIL_BORRAR`(
	IN param_PerfilId int UNSIGNED
)
BEGIN
	DELETE FROM `gestion_carrera_universitaria`.`tblperfil` WHERE PK_Perfil_Id =  param_PerfilId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_PERFIL_LISTAR`()
BEGIN
	SELECT
		gestion_carrera_universitaria.tblperfil.PK_Perfil_Id AS perfilId, 
		gestion_carrera_universitaria.tblperfil.UQ_Perfil_Nombre AS perfilNombre
	FROM
		gestion_carrera_universitaria.tblperfil;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_CREAR`(
	IN param_Nombre varchar(64),
	IN param_Modalidad enum('MP','PH','ED','EDH'),
	IN param_CreditosElectiva int UNSIGNED,
	IN param_CuposVerano int UNSIGNED,
	IN param_CuposNormal int UNSIGNED,
	IN param_CuposSoloEnB int UNSIGNED,
	IN param_CuposIngresante int UNSIGNED,
  OUT outparam_NewId int UNSIGNED
)
BEGIN
	INSERT INTO `gestion_carrera_universitaria`.`tblcarrera` (UQ_Carrera_Nombre, DF_Carrera_Modalidad, DF_Carrera_CreditosElectivas, DF_Carrera_CuposVerano, DF_Carrera_CuposNormal, DF_Carrera_CuposSoloEnB, DF_Carrera_CuposIngresantes) VALUES (param_Nombre, param_modalidad, param_creditosElectiva, param_cuposVerano, param_cuposNormal, param_cuposSoloEnB, param_cuposIngresante);
  SET outparam_NewId = LAST_INSERT_ID();
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_BORRAR`(
	IN param_CarreraId int UNSIGNED
)
BEGIN
	DELETE FROM `gestion_carrera_universitaria`.`tblcarrera` WHERE PK_Carrera_Id =  param_CarreraId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_LISTAR`()
BEGIN
	SELECT
		gestion_carrera_universitaria.tblcarrera.PK_Carrera_Id AS carreraId, 
		gestion_carrera_universitaria.tblcarrera.UQ_Carrera_Nombre AS carreraNombre,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_Modalidad AS carreraModalidad,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_CreditosElectivas AS carreraCreditosElectivas,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposVerano AS carreraCuposVerano,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposNormal AS carreraCuposNormal,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposSoloEnB AS carreraCuposSoloEnB,
		gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposIngresantes AS carrerasCuposIngresantes
	FROM
		gestion_carrera_universitaria.tblcarrera;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_OBTENER`(
  IN param_CarreraId int UNSIGNED
)
BEGIN
  SELECT
    gestion_carrera_universitaria.tblcarrera.PK_Carrera_Id AS carreraId, 
    gestion_carrera_universitaria.tblcarrera.UQ_Carrera_Nombre AS carreraNombre,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_Modalidad AS carreraModalidad,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_CreditosElectivas AS carreraCreditosElectivas,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposVerano AS carreraCuposVerano,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposNormal AS carreraCuposNormal,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposSoloEnB AS carreraCuposSoloEnB,
    gestion_carrera_universitaria.tblcarrera.DF_Carrera_CuposIngresantes AS carrerasCuposIngresantes
  FROM
    gestion_carrera_universitaria.tblcarrera
  WHERE
    gestion_carrera_universitaria.tblcarrera.PK_Carrera_Id = param_CarreraId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_INSTANCIA_EVALUATIVA_CREAR`(
	IN param_CarreraId int UNSIGNED,
	IN param_Nombre varchar(64),
	IN param_InscripcionEnV bool,
	IN param_InscripcionEn1A bool,
	IN param_InscripcionEn1B bool,
	IN param_InscripcionEn2A bool,
	IN param_InscripcionEn2B bool,
	IN param_InscripcionCuatrimestreMinimo int UNSIGNED,
	IN param_Correlativas JSON,
  OUT outparam_NewId int UNSIGNED
)
BEGIN
  DECLARE var_InstanciaEvaluativaId int UNSIGNED;
  DECLARE var_CorrelativaId int UNSIGNED;
	INSERT INTO `gestion_carrera_universitaria`.`tblinstancia` (FK_Instancia_Carrera, UQ_Instancia_Nombre, DF_Instancia_InscripcionEnV, DF_Instancia_InscripcionEn1A, DF_Instancia_InscripcionEn1B, DF_Instancia_InscripcionEn2A, DF_Instancia_InscripcionEn2B, DF_Instancia_InscripcionCuatrimestreMinimo) VALUES (param_CarreraId, param_Nombre, param_InscripcionEnV, param_InscripcionEn1A, param_InscripcionEn1B, param_InscripcionEn2A, param_InscripcionEn2B, param_InscripcionCuatrimestreMinimo);
  SET var_InstanciaEvaluativaId = LAST_INSERT_ID();
  SET outparam_NewId = LAST_INSERT_ID();

  WHILE JSON_LENGTH(param_Correlativas) > 0 DO
    SET var_CorrelativaId = JSON_UNQUOTE(JSON_EXTRACT(param_Correlativas, '$[0]'));
    
    INSERT INTO `gestion_carrera_universitaria`.`tblcorrelativa` (PK_Correlativa_Instancia, PK_Correlativa_InstanciaCorrelativa) VALUES (var_InstanciaEvaluativaId, var_CorrelativaId);
    
    SET param_Correlativas = JSON_REMOVE(param_Correlativas, '$[0]');
  END WHILE;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_MATERIA_CREAR`(
  IN param_CarreraId int UNSIGNED,
  IN param_Nombre varchar(64),
  IN param_InscripcionEnV bool,
  IN param_InscripcionEn1A bool,
  IN param_InscripcionEn1B bool,
  IN param_InscripcionEn2A bool,
  IN param_InscripcionEn2B bool,
  IN param_InscripcionCuatrimestreMinimo int UNSIGNED,
  IN param_Tipologia enum('MEC','MIA','MIP','PROCESO','ELECTIVA'),
  IN param_CuatrimestreEnCarrera int UNSIGNED,
  IN param_GastaCupo bool,
  IN param_Creditos int UNSIGNED,
  IN param_Correlativas JSON,
  OUT outparam_NewId int UNSIGNED
)
BEGIN
  DECLARE var_MateriaId int UNSIGNED;
  DECLARE var_CorrelativaId int UNSIGNED;

  IF param_Tipologia = 'ELECTIVA' THEN
    IF param_Creditos IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las materias electivas deben indicar cuántos creditos aportan';
    END IF;
  ELSE
    IF param_Creditos IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La materia de esta tipología no debe aportar creditos';
    END IF;
  END IF;

  INSERT INTO `gestion_carrera_universitaria`.`tblinstancia` (FK_Instancia_Carrera, UQ_Instancia_Nombre, DF_Instancia_InscripcionEnV, DF_Instancia_InscripcionEn1A, DF_Instancia_InscripcionEn1B, DF_Instancia_InscripcionEn2A, DF_Instancia_InscripcionEn2B, DF_Instancia_InscripcionCuatrimestreMinimo) VALUES (param_CarreraId, param_Nombre, param_InscripcionEnV, param_InscripcionEn1A, param_InscripcionEn1B, param_InscripcionEn2A, param_InscripcionEn2B, param_InscripcionCuatrimestreMinimo);
  SET var_MateriaId = LAST_INSERT_ID();
  SET outparam_NewId = LAST_INSERT_ID();

  INSERT INTO `gestion_carrera_universitaria`.`tblmateria` (PK_Materia_Instancia, DF_Materia_Tipologia, DF_Materia_CuatrimestreEnCarrera, DF_Materia_GastaCupo, DF_Materia_Creditos) VALUES (var_MateriaId, param_Tipologia, param_CuatrimestreEnCarrera, param_GastaCupo, param_Creditos);

  WHILE JSON_LENGTH(param_Correlativas) > 0 DO
    SET var_CorrelativaId = JSON_UNQUOTE(JSON_EXTRACT(param_Correlativas, '$[0]'));
    
    INSERT INTO `gestion_carrera_universitaria`.`tblcorrelativa` (PK_Correlativa_Instancia, PK_Correlativa_InstanciaCorrelativa) VALUES (var_MateriaId, var_CorrelativaId);
    
    SET param_Correlativas = JSON_REMOVE(param_Correlativas, '$[0]');
  END WHILE;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERA_INSTANCIA_LISTAR`(
  IN param_CarreraId int UNSIGNED
)
BEGIN
  SELECT
    instancias.PK_Instancia_Id AS instanciaId,
    instancias.UQ_Instancia_Nombre AS instanciaNombre,
    instancias.DF_Instancia_InscripcionEnV AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionEn1A AS instanciaInscripcionEn1A,
    instancias.DF_Instancia_InscripcionEn1B AS instanciaInscripcionEn1B,
    instancias.DF_Instancia_InscripcionEn2A AS instanciaInscripcionEn2A,
    instancias.DF_Instancia_InscripcionEn2B AS instanciaInscripcionEn2B,
    instancias.DF_Instancia_InscripcionCuatrimestreMinimo AS instanciaCuatrimestreMinimo,
    materias.DF_Materia_Tipologia AS materiaTipologia,
    materias.DF_Materia_CuatrimestreEnCarrera AS materiaCuatrimestreEnCarrera,
    materias.DF_Materia_GastaCupo AS materiaGastaCupo,
    materias.DF_Materia_Creditos AS materiaCreditos,
    FUNCTION_CARRERA_INSTANCIA_LISTAR_CORRELATIVAS(instancias.PK_Instancia_Id) AS instanciaCorrelativas
  FROM
    gestion_carrera_universitaria.tblinstancia AS instancias
    LEFT JOIN gestion_carrera_universitaria.tblmateria AS materias ON instancias.PK_Instancia_Id = materias.PK_Materia_Instancia
  WHERE
    instancias.FK_Instancia_Carrera = param_CarreraId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_INSCRIBIR`(
  IN param_PerfilId int UNSIGNED,
  IN param_CarreraId int UNSIGNED,
  IN param_YearInicio year,
  IN param_PeriodoInicio enum('V','1A','1B','2A','2B')
)
BEGIN
  DECLARE var_CarreraEstudianteYaInscripta int UNSIGNED;

  SELECT COUNT(1) INTO var_CarreraEstudianteYaInscripta FROM gestion_carrera_universitaria.tblcarreraestudiante;

  IF var_CarreraEstudianteYaInscripta > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El perfil ya está inscripto en esta carrera';
  END IF;

  INSERT INTO `gestion_carrera_universitaria`.`tblcarreraestudiante` (FK_CarreraEstudiante_Perfil, FK_CarreraEstudiante_Carrera, DF_CarreraEstudiante_YearInicio, DF_CarreraEstudiante_PeriodoInicio) VALUES (param_PerfilId, param_CarreraId, param_YearInicio, param_PeriodoInicio);
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_DESINSCRIBIR`(
  IN param_PerfilId int UNSIGNED,
  IN param_CarreraId int UNSIGNED
)
BEGIN
  DECLARE var_CarreraEstudianteYaInscripta int UNSIGNED;

  SELECT
    COUNT(1) INTO var_CarreraEstudianteYaInscripta
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante AS gestionCarreraUni
  WHERE
    gestionCarreraUni.FK_CarreraEstudiante_Perfil = param_PerfilId AND gestionCarreraUni.FK_CarreraEstudiante_Carrera = param_CarreraId;

  IF var_CarreraEstudianteYaInscripta = 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El perfil no está inscripto en esta carrera';
  END IF;

  DELETE FROM gestion_carrera_universitaria.tblcarreraestudiante AS gestionCarreraUni WHERE gestionCarreraUni.FK_CarreraEstudiante_Perfil = param_PerfilId AND gestionCarreraUni.FK_CarreraEstudiante_Carrera = param_CarreraId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_LISTAR`(
  IN param_PerfilId int UNSIGNED
)
BEGIN
  SELECT
    carreraEstudiante.PK_CarreraEstudiante_Id AS carreraEstudianteId,
    carreraEstudiante.DF_CarreraEstudiante_YearInicio AS carreraEstudianteYearInicio,
    carreraEstudiante.DF_CarreraEstudiante_PeriodoInicio AS carreraEstudiantePeriodoInicio,
    carrera.UQ_Carrera_Nombre AS carreraNombre,
    carrera.DF_Carrera_Modalidad AS carreraModalidad,
    carrera.DF_Carrera_CreditosElectivas AS carreraCreditosElectivas,
    carrera.DF_Carrera_CuposVerano AS carreraCuposVerano,
    carrera.DF_Carrera_CuposNormal AS carreraCuposNormal,
    carrera.DF_Carrera_CuposSoloEnB AS carreraCuposSoloEnB,
    carrera.DF_Carrera_CuposIngresantes AS carrerasCuposIngresantes
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante AS carreraEstudiante
    INNER JOIN gestion_carrera_universitaria.tblcarrera AS carrera ON carreraEstudiante.FK_CarreraEstudiante_Carrera = carrera.PK_Carrera_Id
  WHERE
    carreraEstudiante.FK_CarreraEstudiante_Perfil = param_PerfilId;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_MODIFICAR_CONFIGURACION`(
  IN param_CarreraEstudianteId int UNSIGNED,
  IN param_CuposEnV int,
  IN param_CuposEn1A int,
  IN param_CuposEn1B int,
  IN param_CuposEn2A int,
  IN param_CuposEn2B int,
  IN param_CreditosExtra int,
  IN param_InstanciasAprobadas JSON
)
BEGIN
  DECLARE var_InstanciaAprobadaId int UNSIGNED;

  INSERT INTO gestion_carrera_universitaria.tblcarreraestudianteconfiguracion
    (PK_CarreraEstudianteConfiguracion_CarreraEstudiante, DF_CarreraEstudianteConfiguracion_CuposEnV, DF_CarreraEstudianteConfiguracion_CuposEn1A, DF_CarreraEstudianteConfiguracion_CuposEn1B, DF_CarreraEstudianteConfiguracion_CuposEn2A, DF_CarreraEstudianteConfiguracion_CuposEn2B, DF_CarreraEstudianteConfiguracion_CreditosExtra)
  VALUES
    (param_CarreraEstudianteId, param_CuposEnV, param_CuposEn1A, param_CuposEn1B, param_CuposEn2A, param_CuposEn2B, param_CreditosExtra)
  ON DUPLICATE KEY UPDATE
    DF_CarreraEstudianteConfiguracion_CuposEnV = param_CuposEnV,
    DF_CarreraEstudianteConfiguracion_CuposEn1A = param_CuposEn1A,
    DF_CarreraEstudianteConfiguracion_CuposEn1B = param_CuposEn1B,
    DF_CarreraEstudianteConfiguracion_CuposEn2A = param_CuposEn2A,
    DF_CarreraEstudianteConfiguracion_CuposEn2B = param_CuposEn2B,
    DF_CarreraEstudianteConfiguracion_CreditosExtra = param_CreditosExtra;

  DELETE FROM gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion WHERE PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = param_CarreraEstudianteId;
  DELETE FROM gestion_carrera_universitaria.tblcarreraestudianteinstanciaaprobada WHERE PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante = param_CarreraEstudianteId;

  WHILE JSON_LENGTH(param_InstanciasAprobadas) > 0 DO
    SET var_InstanciaAprobadaId = JSON_UNQUOTE(JSON_EXTRACT(param_InstanciasAprobadas, '$[0]'));
    
    INSERT INTO `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciaaprobada` (PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante, PK_CarreraEstudianteInstanciaAprobada_Instancia) VALUES (param_CarreraEstudianteId, var_InstanciaAprobadaId);
    
    SET param_InstanciasAprobadas = JSON_REMOVE(param_InstanciasAprobadas, '$[0]');
  END WHILE;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_INSTANCIA_INSCRIBIR`(
  IN param_CarreraEstudianteId int UNSIGNED,
  IN param_InstanciaId int UNSIGNED,
  IN param_Year year,
  IN param_Periodo enum('V','1A','1B','2A','2B')
)
BEGIN
  DECLARE var_CarreraEstudianteInstanciaInscripta int UNSIGNED;

  SELECT
    COUNT(1) INTO var_CarreraEstudianteInstanciaInscripta
  FROM gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion carreraEstudianteInstanciaInscripcion
  WHERE
    carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = param_CarreraEstudianteId AND carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_Instancia = param_InstanciaId;

  IF var_CarreraEstudianteInstanciaInscripta > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El perfil ya está inscripto en esta instancia de esta carrera';
  END IF;

  INSERT INTO `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciainscripcion` (PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante, PK_CarreraEstudianteInstanciaInscripcion_Instancia, DF_CarreraEstudianteInstanciaInscripcion_Year, DF_CarreraEstudianteInstanciaInscripcion_Periodo) VALUES (param_CarreraEstudianteId, param_InstanciaId, param_Year, param_Periodo);
END;
$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=CURRENT_USER PROCEDURE `PROCEDURE_CARRERAESTUDIANTE_INSTANCIA_LISTAR`(
  IN param_CarreraEstudianteId int UNSIGNED
)
BEGIN
  SELECT
    instancias.PK_Instancia_Id AS instanciaId,
    instancias.UQ_Instancia_Nombre AS instanciaNombre,
    instancias.DF_Instancia_InscripcionEnV AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionEn1A AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionEn1B AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionEn2A AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionEn2B AS instanciaInscripcionEnV,
    instancias.DF_Instancia_InscripcionCuatrimestreMinimo AS instanciaCuatrimestreMinimo,
    materias.DF_Materia_Tipologia AS materiaTipologia,
    materias.DF_Materia_CuatrimestreEnCarrera AS materiaCuatrimestreEnCarrera,
    materias.DF_Materia_GastaCupo AS materiaGastaCupo,
    materias.DF_Materia_Creditos AS materiaCreditos,
    FUNCTION_CARRERA_INSTANCIA_LISTAR_CORRELATIVAS(instancias.PK_Instancia_Id) AS instanciaCorrelativas,
    (
      SELECT
        COUNT(1)
      FROM
        gestion_carrera_universitaria.tblcarreraestudianteinstanciaaprobada carreraEstudianteInstanciaAprobada
      WHERE
        carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante = param_CarreraEstudianteId AND carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_Instancia = instancias.PK_Instancia_Id
    ) AS instanciaAprobada,
    (
      SELECT
        carreraEstudianteInstanciaInscripcion.DF_CarreraEstudianteInstanciaInscripcion_Year
      FROM
        gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion carreraEstudianteInstanciaInscripcion
      WHERE
        carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = param_CarreraEstudianteId AND carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_Instancia = instancias.PK_Instancia_Id
    ) AS instanciaInscriptaYear,
    (
      SELECT
        carreraEstudianteInstanciaInscripcion.DF_CarreraEstudianteInstanciaInscripcion_Periodo
      FROM
        gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion carreraEstudianteInstanciaInscripcion
      WHERE
        carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = param_CarreraEstudianteId AND carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_Instancia = instancias.PK_Instancia_Id
    ) AS instanciaInscriptaPeriodo
  FROM
    gestion_carrera_universitaria.tblinstancia AS instancias
    LEFT JOIN gestion_carrera_universitaria.tblmateria AS materias ON instancias.PK_Instancia_Id = materias.PK_Materia_Instancia
  WHERE
    instancias.FK_Instancia_Carrera = param_CarreraEstudianteId;
END;
$$
DELIMITER ;



/*
**** TRIGGERS ****
*/
DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_MODALIDAD_CUPOSSOLOENB_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarrera`
FOR EACH ROW
BEGIN
  IF NEW.DF_Carrera_Modalidad = 'MP' THEN
    IF NEW.DF_Carrera_CuposSoloEnB IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las carreras de modalidad MP no tienen subperíodos "B"';
    END IF;
  ELSE
    IF NEW.DF_Carrera_CuposSoloEnB IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La carrera debe tener un valor no nulo en CuposSoloEnB "B"';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_MODALIDAD_CUPOSSOLOENB_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarrera`
FOR EACH ROW
BEGIN
  IF NEW.DF_Carrera_Modalidad = 'MP' THEN
    IF NEW.DF_Carrera_CuposSoloEnB IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las carreras de modalidad MP no tienen subperíodos "B"';
    END IF;
  ELSE
    IF NEW.DF_Carrera_CuposSoloEnB IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La carrera debe tener un valor no nulo en CuposSoloEnB "B"';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_MATERIA_TIPOLOGIA_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblmateria`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaInscripcionEnV bool;
  DECLARE var_InstanciaInscripcionEn1B bool;
  DECLARE var_InstanciaInscripcionEn2B bool;
  SELECT
    instancia.DF_Instancia_InscripcionEnV,
    instancia.DF_Instancia_InscripcionEn1B,
    instancia.DF_Instancia_InscripcionEn2B
  INTO var_InstanciaInscripcionEnV, var_InstanciaInscripcionEn1B, var_InstanciaInscripcionEn2B
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Materia_Instancia;
  IF NEW.DF_Materia_Tipologia IN ('MIP', 'PROCESO') AND (var_InstanciaInscripcionEnV IS TRUE OR var_InstanciaInscripcionEn1B IS TRUE OR var_InstanciaInscripcionEn2B IS TRUE) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las materias MIP o de Proceso no pueden inscribirse en Verano o períodos B';
  END IF;
  IF NEW.DF_Materia_Tipologia = 'ELECTIVA' THEN
    IF NEW.DF_Materia_Creditos IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las materias electivas deben indicar cuántos créditos otorga';
    END IF;
  ELSE
    IF NEW.DF_Materia_Creditos IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La materia no es electiva por lo que no puede otorgar créditos';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_MATERIA_TIPOLOGIA_UPDATE
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblmateria`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaInscripcionEnV bool;
  DECLARE var_InstanciaInscripcionEn1B bool;
  DECLARE var_InstanciaInscripcionEn2B bool;
  SELECT
    instancia.DF_Instancia_InscripcionEnV,
    instancia.DF_Instancia_InscripcionEn1B,
    instancia.DF_Instancia_InscripcionEn2B
  INTO var_InstanciaInscripcionEnV, var_InstanciaInscripcionEn1B, var_InstanciaInscripcionEn2B
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Materia_Instancia;
  IF NEW.DF_Materia_Tipologia IN ('MIP', 'PROCESO') AND (var_InstanciaInscripcionEnV IS TRUE OR var_InstanciaInscripcionEn1B IS TRUE OR var_InstanciaInscripcionEn2B IS TRUE) THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las materias MIP o de Proceso no pueden inscribirse en Verano o períodos B';
  END IF;
  IF NEW.DF_Materia_Tipologia = 'ELECTIVA' THEN
    IF NEW.DF_Materia_Creditos IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las materias electivas deben indicar cuántos créditos otorga';
    END IF;
  ELSE
    IF NEW.DF_Materia_Creditos IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La materia no es electiva por lo que no puede otorgar créditos';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_INSTANCIA_INSCRIPCIONENB_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblinstancia`
FOR EACH ROW
BEGIN
  DECLARE var_ModalidadCarrera enum('MP','PH','ED','EDH');
  SELECT carrera.DF_Carrera_Modalidad INTO var_ModalidadCarrera FROM gestion_carrera_universitaria.tblcarrera carrera WHERE carrera.PK_Carrera_Id = NEW.FK_Instancia_Carrera;
  IF var_ModalidadCarrera = 'MP' THEN
    IF NEW.DF_Instancia_InscripcionEn1B IS NOT NULL OR NEW.DF_Instancia_InscripcionEn2B IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las instanias en una carrera de modalida MP no pueden inscribirse en períodos "B"';
    END IF;
  ELSE
    IF NEW.DF_Instancia_InscripcionEn1B IS NULL OR NEW.DF_Instancia_InscripcionEn2B IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia debe tener definido si puede ser inscripta en un 1B y 2B';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_INSTANCIA_INSCRIPCIONENB_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblinstancia`
FOR EACH ROW
BEGIN
  DECLARE var_ModalidadCarrera enum('MP','PH','ED','EDH');
  SELECT carrera.DF_Carrera_Modalidad INTO var_ModalidadCarrera FROM gestion_carrera_universitaria.tblcarrera carrera WHERE carrera.PK_Carrera_Id = NEW.FK_Instancia_Carrera;
  IF var_ModalidadCarrera = 'MP' THEN
    IF NEW.DF_Instancia_InscripcionEn1B IS NOT NULL OR NEW.DF_Instancia_InscripcionEn2B IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las instanias en una carrera de modalida MP no pueden inscribirse en períodos "B"';
    END IF;
  ELSE
    IF NEW.DF_Instancia_InscripcionEn1B IS NULL OR NEW.DF_Instancia_InscripcionEn2B IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia debe tener definido si puede ser inscripta en un 1B y 2B';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INICIO_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarreraestudiante`
FOR EACH ROW
BEGIN
  IF NEW.DF_CarreraEstudiante_PeriodoInicio = 'V' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El inicio de una carrera cursada no puede ser en el período de verano';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INICIO_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarreraestudiante`
FOR EACH ROW
BEGIN
  IF NEW.DF_CarreraEstudiante_PeriodoInicio = 'V' THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El inicio de una carrera cursada no puede ser en el período de verano';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_CONFIGURACION_CUPOSENB_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarreraestudianteconfiguracion`
FOR EACH ROW
BEGIN
  DECLARE var_ModalidadCarrera enum('MP','PH','ED','EDH');
  SELECT
    carrera.DF_Carrera_Modalidad INTO var_ModalidadCarrera
  FROM
    gestion_carrera_universitaria.tblcarrera carrera
    INNER JOIN gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante ON carreraEstudiante.FK_CarreraEstudiante_Carrera = carrera.PK_Carrera_Id
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteConfiguracion_CarreraEstudiante;
  IF var_ModalidadCarrera = 'MP' THEN
    IF NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NOT NULL OR NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las carreras de modalida MP no aceptan inscripciones en períodos "B"';
    END IF;
  ELSE
    IF NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NULL OR NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Se debe configurar los cupos para 1B y 2B';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_CONFIGURACION_CUPOSENB_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarreraestudianteconfiguracion`
FOR EACH ROW
BEGIN
  DECLARE var_ModalidadCarrera enum('MP','PH','ED','EDH');
  SELECT
    carrera.DF_Carrera_Modalidad INTO var_ModalidadCarrera
  FROM
    gestion_carrera_universitaria.tblcarrera carrera
    INNER JOIN gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante ON carreraEstudiante.FK_CarreraEstudiante_Carrera = carrera.PK_Carrera_Id
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteConfiguracion_CarreraEstudiante;
  IF var_ModalidadCarrera = 'MP' THEN
    IF NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NOT NULL OR NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NOT NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las carreras de modalida MP no aceptan inscripciones en períodos "B"';
    END IF;
  ELSE
    IF NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NULL OR NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NULL THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Se debe configurar los cupos para 1B y 2B';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_CONFIGURACION_CUPOS_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarreraestudianteconfiguracion`
FOR EACH ROW
BEGIN
  DECLARE var_CarreraCuposNormal int UNSIGNED;
  DECLARE var_CarreraCuposVerano int UNSIGNED;
  SELECT
    carrera.DF_Carrera_CuposNormal,
    carrera.DF_Carrera_CuposVerano
  INTO
    var_CarreraCuposNormal, var_CarreraCuposVerano
  FROM
    gestion_carrera_universitaria.tblcarrera carrera
    INNER JOIN gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante ON carreraEstudiante.FK_CarreraEstudiante_Carrera = carrera.PK_Carrera_Id
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteConfiguracion_CarreraEstudiante;
  IF
    NEW.DF_CarreraEstudianteConfiguracion_CuposEn1A > var_CarreraCuposNormal OR (NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NOT NULL AND NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B > var_CarreraCuposNormal) OR
    NEW.DF_CarreraEstudianteConfiguracion_CuposEn2A > var_CarreraCuposNormal OR (NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NOT NULL AND NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B > var_CarreraCuposNormal) OR
    NEW.DF_CarreraEstudianteConfiguracion_CuposEnV > var_CarreraCuposVerano
  THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede configurar una cantidad de cupos mayor al indicando en la carrera';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_CONFIGURACION_CUPOS_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarreraestudianteconfiguracion`
FOR EACH ROW
BEGIN
  DECLARE var_CarreraCuposNormal int UNSIGNED;
  DECLARE var_CarreraCuposVerano int UNSIGNED;
  SELECT
    carrera.DF_Carrera_CuposNormal,
    carrera.DF_Carrera_CuposVerano
  INTO
    var_CarreraCuposNormal, var_CarreraCuposVerano
  FROM
    gestion_carrera_universitaria.tblcarrera carrera
    INNER JOIN gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante ON carreraEstudiante.FK_CarreraEstudiante_Carrera = carrera.PK_Carrera_Id
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteConfiguracion_CarreraEstudiante;
  IF
    NEW.DF_CarreraEstudianteConfiguracion_CuposEn1A > var_CarreraCuposNormal OR (NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B IS NOT NULL AND NEW.DF_CarreraEstudianteConfiguracion_CuposEn1B > var_CarreraCuposNormal) OR
    NEW.DF_CarreraEstudianteConfiguracion_CuposEn2A > var_CarreraCuposNormal OR (NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B IS NOT NULL AND NEW.DF_CarreraEstudianteConfiguracion_CuposEn2B > var_CarreraCuposNormal) OR
    NEW.DF_CarreraEstudianteConfiguracion_CuposEnV > var_CarreraCuposVerano
  THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede configurar una cantidad de cupos mayor al indicando en la carrera';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INSTANCIA_INSCRIPCION_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciainscripcion`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaYaAprobada int UNSIGNED;
  DECLARE var_InstanciaInscripcionEnV bool;
  DECLARE var_InstanciaInscripcionEn1A bool;
  DECLARE var_InstanciaInscripcionEn1B bool;
  DECLARE var_InstanciaInscripcionEn2A bool;
  DECLARE var_InstanciaInscripcionEn2B bool;
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteYearInicio year;
  DECLARE var_CarreraEstudiantePeriodoInicio enum('V','1A','1B','2A','2B');
  SELECT
    COUNT(1) INTO var_InstanciaYaAprobada
  FROM
    gestion_carrera_universitaria.tblcarreraestudianteinstanciaaprobada carreraEstudianteInstanciaAprobada
    INNER JOIN gestion_carrera_universitaria.tblinstancia instancia ON instancia.PK_Instancia_Id = carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_Instancia
  WHERE
    carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante = NEW.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante AND
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_Instancia;
  IF var_InstanciaYaAprobada > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia a inscribir ya está aprobada por el estudiante';
  END IF;
  SELECT
    instancia.DF_Instancia_InscripcionEnV,
    instancia.DF_Instancia_InscripcionEn1A,
    instancia.DF_Instancia_InscripcionEn1B,
    instancia.DF_Instancia_InscripcionEn2A,
    instancia.DF_Instancia_InscripcionEn2B,
    instancia.FK_Instancia_Carrera
  INTO var_InstanciaInscripcionEnV, var_InstanciaInscripcionEn1A, var_InstanciaInscripcionEn1B, var_InstanciaInscripcionEn2A, var_InstanciaInscripcionEn2B, var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_Instancia;
  IF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = 'V' AND var_InstanciaInscripcionEnV IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período de Verano';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '1A' AND var_InstanciaInscripcionEn1A IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 1A';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '1B' AND var_InstanciaInscripcionEn1B IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 1B';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '2A' AND var_InstanciaInscripcionEn2A IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 2A';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '2B' AND var_InstanciaInscripcionEn2B IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 2B';
  END IF;
  SELECT
    carreraEstudiante.FK_CarreraEstudiante_Carrera,
    carreraEstudiante.DF_CarreraEstudiante_YearInicio,
    carreraEstudiante.DF_CarreraEstudiante_PeriodoInicio
  INTO var_CarreraEstudianteCarreraId, var_CarreraEstudianteYearInicio, var_CarreraEstudiantePeriodoInicio
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante;
  IF var_InstanciaCarreraId != var_CarreraEstudianteCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La materia a inscribir no pertenece a la carrera indicada';
  END IF;
  IF var_CarreraEstudianteYearInicio > NEW.DF_CarreraEstudianteInstanciaInscripcion_Year THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
  ELSEIF var_CarreraEstudianteYearInicio = NEW.DF_CarreraEstudianteInstanciaInscripcion_Year THEN
    IF var_CarreraEstudiantePeriodoInicio = '1A' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '1B' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '2A' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A', '1B') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '2B' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A', '1B', '2A') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INSTANCIA_INSCRIPCION_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciainscripcion`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaYaAprobada int UNSIGNED;
  DECLARE var_InstanciaInscripcionEnV bool;
  DECLARE var_InstanciaInscripcionEn1A bool;
  DECLARE var_InstanciaInscripcionEn1B bool;
  DECLARE var_InstanciaInscripcionEn2A bool;
  DECLARE var_InstanciaInscripcionEn2B bool;
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteYearInicio year;
  DECLARE var_CarreraEstudiantePeriodoInicio enum('V','1A','1B','2A','2B');
  SELECT
    COUNT(1) INTO var_InstanciaYaAprobada
  FROM
    gestion_carrera_universitaria.tblcarreraestudianteinstanciaaprobada carreraEstudianteInstanciaAprobada
    INNER JOIN gestion_carrera_universitaria.tblinstancia instancia ON instancia.PK_Instancia_Id = carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_Instancia
  WHERE
    carreraEstudianteInstanciaAprobada.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante = NEW.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante AND
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_Instancia;
  IF var_InstanciaYaAprobada > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia a inscribir ya está aprobada por el estudiante';
  END IF;
  SELECT
    instancia.DF_Instancia_InscripcionEnV,
    instancia.DF_Instancia_InscripcionEn1A,
    instancia.DF_Instancia_InscripcionEn1B,
    instancia.DF_Instancia_InscripcionEn2A,
    instancia.DF_Instancia_InscripcionEn2B,
    instancia.FK_Instancia_Carrera
  INTO var_InstanciaInscripcionEnV, var_InstanciaInscripcionEn1A, var_InstanciaInscripcionEn1B, var_InstanciaInscripcionEn2A, var_InstanciaInscripcionEn2B, var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_Instancia;
  IF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = 'V' AND var_InstanciaInscripcionEnV IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período de Verano';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '1A' AND var_InstanciaInscripcionEn1A IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 1A';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '1B' AND var_InstanciaInscripcionEn1B IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 1B';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '2A' AND var_InstanciaInscripcionEn2A IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 2A';
  ELSEIF NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo = '2B' AND var_InstanciaInscripcionEn2B IS FALSE THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No es posible inscribir esta instancia en el período 2B';
  END IF;
  SELECT
    carreraEstudiante.FK_CarreraEstudiante_Carrera,
    carreraEstudiante.DF_CarreraEstudiante_YearInicio,
    carreraEstudiante.DF_CarreraEstudiante_PeriodoInicio
  INTO var_CarreraEstudianteCarreraId, var_CarreraEstudianteYearInicio, var_CarreraEstudiantePeriodoInicio
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante;
  IF var_InstanciaCarreraId != var_CarreraEstudianteCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia a inscribir no pertenece a la carrera indicada';
  END IF;
  IF var_CarreraEstudianteYearInicio > NEW.DF_CarreraEstudianteInstanciaInscripcion_Year THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
  ELSEIF var_CarreraEstudianteYearInicio = NEW.DF_CarreraEstudianteInstanciaInscripcion_Year THEN
    IF var_CarreraEstudiantePeriodoInicio = '1A' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '1B' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '2A' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A', '1B') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    ELSEIF var_CarreraEstudiantePeriodoInicio = '2B' AND NEW.DF_CarreraEstudianteInstanciaInscripcion_Periodo IN ('V', '1A', '1B', '2A') THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No puede inscribir una instancia antes de haber iniciado la carrera';
    END IF;
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INSTANCIA_APROBADA_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciaaprobada`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaInscripta int UNSIGNED;
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteCarreraId int UNSIGNED;
  SELECT
    COUNT(1) INTO var_InstanciaInscripta
  FROM
    gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion carreraEstudianteInstanciaInscripcion
    INNER JOIN gestion_carrera_universitaria.tblinstancia instancia ON instancia.PK_Instancia_Id = carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_Instancia
  WHERE
    carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = NEW.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante AND
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_Instancia;
  IF var_InstanciaInscripta > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia marcar como aprobada figura en una inscripción';
  END IF;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_Instancia;
  SELECT
    carreraEstudiante.FK_CarreraEstudiante_Carrera
  INTO var_CarreraEstudianteCarreraId
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante;
  IF var_InstanciaCarreraId != var_CarreraEstudianteCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia a aprobar no pertenece a la carrera indicada';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERAESTUDIANTE_INSTANCIA_APROBADA_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcarreraestudianteinstanciaaprobada`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaInscripta int UNSIGNED;
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_CarreraEstudianteCarreraId int UNSIGNED;
  SELECT
    COUNT(1) INTO var_InstanciaInscripta
  FROM
    gestion_carrera_universitaria.tblcarreraestudianteinstanciainscripcion carreraEstudianteInstanciaInscripcion
    INNER JOIN gestion_carrera_universitaria.tblinstancia instancia ON instancia.PK_Instancia_Id = carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_Instancia
  WHERE
    carreraEstudianteInstanciaInscripcion.PK_CarreraEstudianteInstanciaInscripcion_CarreraEstudiante = NEW.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante AND
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_Instancia;
  IF var_InstanciaInscripta > 0 THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia marcar como aprobada figura en una inscripción';
  END IF;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_Instancia;
  SELECT
    carreraEstudiante.FK_CarreraEstudiante_Carrera
  INTO var_CarreraEstudianteCarreraId
  FROM
    gestion_carrera_universitaria.tblcarreraestudiante carreraEstudiante
  WHERE
    carreraEstudiante.PK_CarreraEstudiante_Id = NEW.PK_CarreraEstudianteInstanciaAprobada_CarreraEstudiante;
  IF var_InstanciaCarreraId != var_CarreraEstudianteCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'La instancia a aprobar no pertenece a la carrera indicada';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_INSTANCIA_CORRELATIVA_INSERT
BEFORE INSERT ON `gestion_carrera_universitaria`.`tblcorrelativa`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_InstanciaCorrelativaCarreraId int UNSIGNED;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO
    var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Correlativa_Instancia;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO
    var_InstanciaCorrelativaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Correlativa_InstanciaCorrelativa;
  IF var_InstanciaCarreraId != var_InstanciaCorrelativaCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las instancias no pertenecen a la misma carrera';
  END IF;
END;
$$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER TRIGGER_CARRERA_INSTANCIA_CORRELATIVA_UPDATE
BEFORE UPDATE ON `gestion_carrera_universitaria`.`tblcorrelativa`
FOR EACH ROW
BEGIN
  DECLARE var_InstanciaCarreraId int UNSIGNED;
  DECLARE var_InstanciaCorrelativaCarreraId int UNSIGNED;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO
    var_InstanciaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Correlativa_Instancia;
  SELECT
    instancia.FK_Instancia_Carrera
  INTO
    var_InstanciaCorrelativaCarreraId
  FROM
    gestion_carrera_universitaria.tblinstancia instancia
  WHERE
    instancia.PK_Instancia_Id = NEW.PK_Correlativa_InstanciaCorrelativa;
  IF var_InstanciaCarreraId != var_InstanciaCorrelativaCarreraId THEN
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Las instancias no pertenecen a la misma carrera';
  END IF;
END;
$$
DELIMITER ;