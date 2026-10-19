# Plan v3: Corrección de Aislamiento de Mixins y Estabilidad de Carga

## 1. Problema Identificado
Al cargar el mod `TacticalWorkbench-fabric-0.1.0-alpha.jar` en un cliente de producción con Fabric Loader 0.20.0, se produjo el siguiente error fatal en la fase `main`:
`org.spongepowered.asm.mixin.transformer.throwables.IllegalClassLoadError: dev.itsrealperson.tacticalworkbench.fabric.TacticalWorkbenchFabric is in a defined mixin package dev.itsrealperson.tacticalworkbench.fabric.* owned by tactical_workbench-fabric.mixins.json and cannot be referenced directly`

## 2. Causa Raíz
SpongePowered Mixin exige que las clases regulares (como los `ModInitializer` de Fabric o los `@Mod` de Forge) residan **fuera** de los paquetes declarados en los archivos `.mixins.json`. La configuración previa declaraba `"package": "dev.itsrealperson.tacticalworkbench.fabric"` en Fabric y `"dev.itsrealperson.tacticalworkbench"` en Common, bloqueando el cargador de clases.

## 3. Acciones Ejecutadas
1. **Aislamiento en `common`**:
   - `CameraAccessor` trasladado a `dev.itsrealperson.tacticalworkbench.mixin.CameraAccessor`.
   - `tactical_workbench-common.mixins.json` configurado con `"package": "dev.itsrealperson.tacticalworkbench.mixin"`.
2. **Aislamiento en `fabric`**:
   - `tactical_workbench-fabric.mixins.json` configurado con `"package": "dev.itsrealperson.tacticalworkbench.fabric.client.mixin"`.
3. **Aislamiento en `forge`**:
   - `tactical_workbench-forge.mixins.json` configurado preventivamente con `"package": "dev.itsrealperson.tacticalworkbench.forge.mixin"`.
4. **Verificación**:
   - Compilación exitosa para todas las plataformas (`./gradlew build` en 34s).
