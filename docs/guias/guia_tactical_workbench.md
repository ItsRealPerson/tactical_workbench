# Guía Completa: Tactical Workbench (Sistema Diegético 1.20.1 Forge & Fabric)

## 1. Visión y Resumen Técnico
El mod **Tactical Workbench** replica y amplía el concepto de estación de trabajo diegética presentado en *Rot n' Remnants* (por Grimm), inspirado a su vez en *The Last of Us*, *Metro Exodus* y *Escape from Tarkov*.

A diferencia de las mesas de trabajo tradicionales de Minecraft que abren una pantalla 2D oscura y desconectada del mundo, el **Tactical Workbench** mantiene al jugador 100% inmerso dentro del espacio tridimensional.

---

## 2. Los 5 Pilares de la Experiencia Diegética

### A. Anclaje Físico y Asiento (`WorkbenchSeatEntity`)
* Al pulsar clic derecho sobre el bloque con facing orientable, el servidor verifica si el jugador ya está sentado.
* Si no lo está, genera una entidad invisible `WorkbenchSeatEntity` frente al banco de trabajo y monta al jugador (`player.startRiding(seat)`).
* El jugador queda físicamente posicionado ante la mesa sin poder caminar libremente, anclando su cuerpo.
* Al presionar `ESC` o desmontarse, la entidad se descarta limpiamente y reproduce el sonido de levantarse (`seat_stand`).

### B. Cinemática de Cámara Suave (`DiegeticCameraController`)
* Al abrir la estación, la cámara no salta bruscamente:
  - Se calcula el vector objetivo:
    $$\text{TargetPos} = \text{PosiciónMesa} + \text{FacingOffset} + (0.0,\, 0.75,\, 0.35)$$
    $$\text{Pitch} \approx 38^\circ, \quad \text{Yaw} = \text{FacingAngle}$$
  - Se interpola mediante una curva cúbica Hermite / Bézier (`easeInOutCubic`):
    $$t < 0.5 \implies 4t^3, \quad t \ge 0.5 \implies 1 - \frac{(-2t + 2)^3}{2}$$
* **Parallax de Cabeza elástico (*Head Parallax*)**:
  - Al arrastrar el ratón mientras se está en la mesa, la vista oscila sutilmente dentro de un cono elástico de $\pm 14^\circ$, volviendo lentamente al centro si no se mueve el ratón, simulando la vista natural de un operador inspeccionando su mesa de trabajo.

### C. Renderizado 3D en Mesa con Sockets (`TacticalWorkbenchRenderer`)
* El bloque dispone de un `BlockEntityRenderer` dedicado.
* El arma principal (`SLOT_PRIMARY`) se renderiza apoyada horizontalmente en el centro del tapete de mantenimiento.
* Los accesorios instalados en el NBT se renderizan en sus respectivos puntos de anclaje (*sockets*):
  - **Riel Superior (Optic Socket)**: Mira réflex (`SLOT_OPTIC`).
  - **Boca de Fuego (Muzzle Socket)**: Silenciador táctico (`SLOT_BARREL`).
  - **Riel Inferior (Underbarrel Socket)**: Empuñadura vertical (`SLOT_UNDERBARREL`).
* Todo el conjunto se orienta automáticamente según la rotación del bloque (`FACING`).

### D. Pantalla Inmersiva sin Oscurecimiento (`WorkbenchScreen`)
* La clase sobreescribe `renderBackground(GuiGraphics)` dejándola vacía, eliminando por completo el oscurecimiento negro por defecto de Minecraft.
* Se dibuja una interfaz táctica translúcida con estilo militar/industrial:
  - Brackets angulares en las esquinas.
  - Cajas interactivas para cada slot con tooltips contextuales.
  - Barra de condición y limpieza dinámica:
    * **Verde** (> 70%): Impecable / Óptima.
    * **Amarillo** (30% - 70%): Operativa / Requiere mantenimiento.
    * **Rojo** (<= 30%): Ensuciada / Riesgo de encasquillamiento.
  - Indicador de desgaste mecánico (`wear`).

### E. Diseño Sonoro Foley Táctil (`ModSounds`)
* Cada interacción cuenta con un evento de sonido realista calibrado:
  - Sentarse / Levantarse: `seat_sit`, `seat_stand`.
  - Instalar / Desmontar accesorio: `attach_equip`, `attach_remove`.
  - Operación mecánica: `bolt_cycle` (cerrojo).
  - Mantenimiento: `oil_wipe` (engrase sintético con trapo), `screw_turn` (ajuste con llave/destornillador).

---

## 3. Guía de Crafteo y Uso en Supervivencia

### Recetas de Crafteo
1. **Tactical Workbench**:
   - 3 Lingotes de Hierro (fila superior) + 1 Mesa de Crafteo (centro) + 3 Tablones de Madera (soporte y patas).
2. **Tactical Rifle**:
   - 3 Lingotes de Hierro + 1 Polvo de Redstone + 1 Palanca + 2 Tablones.
3. **Reflex Sight (Mira Réflex)**:
   - 4 Pepitas de Hierro + 2 Polvo de Redstone + 1 Panel de Cristal.
4. **Suppressor (Silenciador)**:
   - 8 Pepitas de Hierro + 1 Bloque de Lana (núcleo disipador).
5. **Vertical Grip (Empuñadura)**:
   - 3 Pepitas de Hierro + 2 Carbón (polímero reforzado).
6. **Gun Oil (Aceite Sintético)**:
   - 1 Frasco de Cristal + 1 Panal + 1 Carbón vegetal.

### Pasos de Uso
1. Coloca el **Tactical Workbench** en tu base o refugio.
2. Haz clic derecho para sentarte frente a él. La cámara hará una transición suave hacia el tapete de trabajo.
3. Coloca tu fusil táctico en la ranura principal.
4. Equipa la mira réflex, el silenciador o la empuñadura con un solo clic. Observa cómo aparecen físicamente montados en el arma en la mesa.
5. Usa los botones tácticos para limpiar el arma, ciclar el cerrojo o ajustar los tornillos.
6. Pulsa `ESC` o haz clic en "Salir [ESC]" para levantarte y volver a la vista en primera persona.

---

## 4. Instrucciones de Compilación y Ejecución

### Compilar para ambas plataformas:
```bash
./gradlew build
```
Los archivos JAR compilados y remapeados se generarán en:
- `fabric/build/libs/TacticalWorkbench-fabric-0.1.0-alpha.jar`
- `forge/build/libs/TacticalWorkbench-forge-0.1.0-alpha.jar`

### Probar en entorno de desarrollo:
```bash
# Probar en Fabric
./gradlew :fabric:runClient

# Probar en Forge
./gradlew :forge:runClient
```
