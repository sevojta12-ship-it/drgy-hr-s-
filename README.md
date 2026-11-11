# Sheriff's Hat (Forge 1.20.1 + Curios)

Two blocky hats worn in the **Curios head slot**:

| Item | Armor | Toughness | Knockback res. |
|------|-------|-----------|----------------|
| Sheriff's Hat | +2 | - | +5% |
| Sheriff's Hat (Badge) | +3 | +1 | +10% |

Crafting: plain = 5-leather hat shape + brown dye in the middle (" L " / "LDL" / "LLL").
Badge = plain hat + gold ingot (shapeless).

## Build
1. Easiest: download the official Forge 1.20.1 MDK (it contains gradlew), then copy this project's
   `src/`, `tools/`, `build.gradle`, `gradle.properties`, `settings.gradle` over it.
   (Or run `gradle wrapper --gradle-version 8.1.1` in this folder if you have Gradle installed.)
2. `./gradlew build` -> jar in `build/libs/`. `./gradlew runClient` to test.
3. Install the jar together with Curios (5.x for 1.20.1) in a Forge 1.20.1 instance.

## Editing the model / texture
`tools/generate_assets.py` regenerates the texture and both inventory models from one cuboid list.
`SheriffHatModel.java` holds the same cuboids for the worn model - change both together.
The item JSONs open in Blockbench. Colors are at the top of the script.

## Build without installing anything (GitHub)
Upload this folder to a new GitHub repository (drag & drop in the browser works). Open the **Actions** tab,
wait for "Build mod jar" to finish, and download the `sheriffhat-jar` artifact. That zip contains the mod jar.

## Blockbench
File > Open Model > `src/main/resources/assets/sheriffhat/models/item/sheriff_hat_star.json`,
then import `textures/item/sheriff_hat.png` in the Textures panel and assign it to the "0" slot.
