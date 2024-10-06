/*
Alumno: Induni, Francisco Gustavo
Legajo: VINF014188
*/

SET @param_Nombre = 'Licenciatura en Informática';
SET @param_Modalidad = 'EDH';
SET @param_CreditosElectiva = '3';
SET @param_CuposVerano = '2';
SET @param_CuposNormal = '3';
SET @param_CuposSoloEnB = '2';
SET @param_CuposIngresante = '3';
CALL `PROCEDURE_CARRERA_CREAR`(@param_Nombre, @param_Modalidad, @param_CreditosElectiva, @param_CuposVerano, @param_CuposNormal, @param_CuposSoloEnB, @param_CuposIngresante);


SET @param_CarreraId = '1';
SET @param_Nombre = 'Análisis de Software';
SET @param_InscripcionEnV = '0';
SET @param_InscripcionEn1A = '1';
SET @param_InscripcionEn1B = '0';
SET @param_InscripcionEn2A = '1';
SET @param_InscripcionEn2B = '0';
SET @param_InscripcionCuatrimestreMinimo = '0';
SET @param_Tipologia = 'MIP';
SET @param_CuatrimestreEnCarrera = '1';
SET @param_GastaCupo = '1';
SET @param_Creditos = NULL;
SET @param_Correlativas = '[]';
CALL `PROCEDURE_CARRERA_MATERIA_CREAR`(@param_CarreraId, @param_Nombre, @param_InscripcionEnV, @param_InscripcionEn1A, @param_InscripcionEn1B, @param_InscripcionEn2A, @param_InscripcionEn2B, @param_InscripcionCuatrimestreMinimo, @param_Tipologia, @param_CuatrimestreEnCarrera, @param_GastaCupo, @param_Creditos, @param_Correlativas);

SET @param_CarreraId = '1';
SET @param_Nombre = 'Seminario de Práctica';
SET @param_InscripcionEnV = '0';
SET @param_InscripcionEn1A = '1';
SET @param_InscripcionEn1B = '0';
SET @param_InscripcionEn2A = '1';
SET @param_InscripcionEn2B = '0';
SET @param_InscripcionCuatrimestreMinimo = '1';
SET @param_Tipologia = 'PROCESO';
SET @param_CuatrimestreEnCarrera = '2';
SET @param_GastaCupo = '1';
SET @param_Creditos = NULL;
SET @param_Correlativas = '[1]';
CALL `PROCEDURE_CARRERA_MATERIA_CREAR`(@param_CarreraId, @param_Nombre, @param_InscripcionEnV, @param_InscripcionEn1A, @param_InscripcionEn1B, @param_InscripcionEn2A, @param_InscripcionEn2B, @param_InscripcionCuatrimestreMinimo, @param_Tipologia, @param_CuatrimestreEnCarrera, @param_GastaCupo, @param_Creditos, @param_Correlativas);

SET @param_CarreraId = '1';
SET @param_Nombre = 'EFIP';
SET @param_InscripcionEnV = '1';
SET @param_InscripcionEn1A = '1';
SET @param_InscripcionEn1B = '1';
SET @param_InscripcionEn2A = '1';
SET @param_InscripcionEn2B = '1';
SET @param_InscripcionCuatrimestreMinimo = '2';
SET @param_Correlativas = '[1,2]';
CALL `PROCEDURE_CARRERA_INSTANCIA_EVALUATIVA_CREAR`(@param_CarreraId, @param_Nombre, @param_InscripcionEnV, @param_InscripcionEn1A, @param_InscripcionEn1B, @param_InscripcionEn2A, @param_InscripcionEn2B, @param_InscripcionCuatrimestreMinimo, @param_Correlativas);

CALL `PROCEDURE_CARRERA_LISTAR`();

SET @param_CarreraId = '1';
CALL `PROCEDURE_CARRERA_INSTANCIA_LISTAR`(@param_CarreraId);

SET @param_Nombre = 'Francisco Induni';
CALL `PROCEDURE_PERFIL_CREAR`(@param_Nombre);

CALL `PROCEDURE_PERFIL_LISTAR`();

SET @param_PerfilId = '1';
SET @param_CarreraId = '1';
SET @param_YearInicio = '2022';
SET @param_PeriodoInicio = '1B';
CALL `PROCEDURE_CARRERAESTUDIANTE_INSCRIBIR`(@param_PerfilId, @param_CarreraId, @param_YearInicio, @param_PeriodoInicio);

SET @param_CarreraEstudianteId = '1';
SET @param_CuposEnV = '1';
SET @param_CuposEn1A = '2';
SET @param_CuposEn1B = '1';
SET @param_CuposEn2A = '2';
SET @param_CuposEn2B = '1';
SET @param_CreditosExtra = '2';
SET @param_InstanciasAprobadas = '[1]';
CALL `PROCEDURE_CARRERAESTUDIANTE_MODIFICAR_CONFIGURACION`(@param_CarreraEstudianteId, @param_CuposEnV, @param_CuposEn1A, @param_CuposEn1B, @param_CuposEn2A, @param_CuposEn2B, @param_CreditosExtra, @param_InstanciasAprobadas);

SET @param_CarreraEstudianteId = '1';
SET @param_InstanciaId = '2';
SET @param_Year = '2023';
SET @param_Periodo = '2A';
CALL `PROCEDURE_CARRERAESTUDIANTE_INSTANCIA_INSCRIBIR`(@param_CarreraEstudianteId, @param_InstanciaId, @param_Year, @param_Periodo);

SET @param_PerfilId = '1';
CALL `PROCEDURE_CARRERAESTUDIANTE_LISTAR`(@param_PerfilId);

SET @param_CarreraEstudianteId = '1';
CALL `PROCEDURE_CARRERAESTUDIANTE_INSTANCIA_LISTAR`(@param_CarreraEstudianteId);

SET @param_PerfilId = '1';
SET @param_CarreraId = '1';
CALL `PROCEDURE_CARRERAESTUDIANTE_DESINSCRIBIR`(@param_PerfilId, @param_CarreraId);

SET @param_PerfilId = '1';
CALL `PROCEDURE_PERFIL_BORRAR`(@param_PerfilId);

SET @param_CarreraId = '1';
CALL `PROCEDURE_CARRERA_BORRAR`(@param_CarreraId);