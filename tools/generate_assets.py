#!/usr/bin/env python3
"""Generates the 128x128 texture and both item JSON models from one cuboid list.
Keep CUBES in sync with SheriffHatModel.java. Requires Pillow."""
import json, os, random
from PIL import Image

HERE = os.path.dirname(os.path.abspath(__file__))
RES = os.path.join(HERE, "..", "src", "main", "resources", "assets", "sheriffhat")
MOD, S = "sheriffhat", 128
rng = random.Random(1337)
Z = (0, 0, 0)

CUBES = [
    dict(name="brim",      uv=(0, 0),   pos=(-9, -8, -9),      size=(18, 1, 18), grow=Z),
    dict(name="lip_front", uv=(0, 20),  pos=(-7, -7, -9),      size=(14, 1, 1),  grow=Z),
    dict(name="lip_back",  uv=(0, 20),  pos=(-7, -7, 8),       size=(14, 1, 1),  grow=Z),
    dict(name="lip_left",  uv=(0, 23),  pos=(-9, -9, -7),      size=(1, 1, 14),  grow=Z),
    dict(name="lip_right", uv=(0, 23),  pos=(8, -9, -7),       size=(1, 1, 14),  grow=Z),
    dict(name="crown",     uv=(0, 40),  pos=(-4, -12, -4),     size=(8, 4, 8),   grow=(.6, .6, .6)),
    dict(name="crease_l",  uv=(0, 53),  pos=(-3.5, -13.5, -3.5), size=(3, 2, 7), grow=Z),
    dict(name="crease_r",  uv=(0, 53),  pos=(0.5, -13.5, -3.5),  size=(3, 2, 7), grow=Z),
    dict(name="cord",      uv=(0, 63),  pos=(-4, -9.5, -4),    size=(8, 1, 8),   grow=(.78, .1, .78)),
    dict(name="cord_knot", uv=(0, 73), pos=(-1.5, -10, -5.78), size=(3, 2, 1), grow=Z),
    dict(name="tail1a", uv=(8, 73), pos=(-1.5, -9, -7.8), size=(1, 1, 2), grow=Z),
    dict(name="tail1b", uv=(8, 73), pos=(-2.5, -9, -7.8), size=(1, 1, 1), grow=Z),
    dict(name="tail1c", uv=(8, 73), pos=(-2.5, -9, -8.8), size=(1, 1, 1), grow=Z),
    dict(name="tail2a", uv=(8, 73), pos=(0.5, -9, -7.8), size=(1, 1, 2), grow=Z),
    dict(name="tail2b", uv=(8, 73), pos=(1.5, -9, -7.8), size=(1, 1, 1), grow=Z),
    dict(name="tail2c", uv=(8, 73), pos=(1.5, -9, -8.8), size=(1, 1, 1), grow=Z),
]
STAR = dict(name="badge", uv=(0, 78), pos=(-1.5, -13, -5.6), size=(3, 3, 1), grow=Z)

for _c in CUBES + [STAR]:
    _c["pos"] = (_c["pos"][0], _c["pos"][1] + 1, _c["pos"][2])   # hat sits 1px lower on the head

# chocolate felt + metallic gold
FELT, FELT_LT, FELT_DK, FELT_UN = (98, 62, 42), (118, 78, 52), (68, 42, 28), (52, 33, 22)
GOLD, GOLD_HI, GOLD_DK = (232, 184, 52), (255, 236, 130), (150, 104, 18)

img = Image.new("RGBA", (S, S), (0, 0, 0, 0))
px = img.load()
def put(x, y, c): px[x, y] = tuple(max(0, min(255, int(v))) for v in c) + (255,)
def shade(c, f): return tuple(v * f for v in c)
def fill(x0, y0, x1, y1, base, var=5):
    for y in range(y0, y1):
        for x in range(x0, x1):
            n = rng.randint(-var, var); put(x, y, (base[0]+n, base[1]+n, base[2]+n))

# brim 18x1x18 @ (0,0): top (18,0), bottom (36,0), edge row y=18
fill(0, 0, 72, 19, FELT); fill(36, 0, 54, 18, FELT_UN); fill(0, 18, 72, 19, FELT_DK)
for j in range(18):
    for i in range(18):
        e = min(i, j, 17 - i, 17 - j)
        if e == 0: put(18 + i, j, shade(FELT_DK, 1 + rng.uniform(-.04, .04)))
        elif e == 2: put(18 + i, j, shade(FELT, .88))     # stitched rows near the edge
        elif e == 3: put(18 + i, j, shade(FELT, 1.08))
fill(0, 20, 30, 22, FELT, 4)       # lips
fill(0, 23, 30, 38, FELT, 4)
# lower crown 8x4x8 @ (0,40)
fill(0, 40, 32, 52, FELT); fill(8, 40, 16, 48, FELT_LT); fill(16, 40, 24, 48, FELT_UN)
for y in range(48, 52):
    f = [1.06, 1.02, .97, .92][y - 48]
    for x in range(32):
        r, g, b, _ = px[x, y]; put(x, y, (r*f, g*f, b*f))
for y in range(48, 52):            # pinched front
    for x in (8, 9, 14, 15):
        r, g, b, _ = px[x, y]; put(x, y, (r*.9, g*.9, b*.9))
# crease halves 3x2x7 @ (0,53)
fill(0, 53, 20, 62, FELT); fill(7, 53, 10, 60, FELT_LT); fill(10, 53, 13, 60, FELT_UN)
# gold braided cord 8x1x8 @ (0,63)
BR = [GOLD, GOLD_HI, GOLD, GOLD_DK]
for y in range(63, 72):
    for x in range(32): put(x, y, BR[(x + y) % 4])
# knot 2x2x1 @ (0,73), ends 1x1x2 @ (8,73)
fill(0, 73, 8, 76, GOLD, 6); put(2, 75, GOLD_HI); put(3, 75, GOLD_HI)
for y in range(73, 76):
    for x in range(8, 14): put(x, y, BR[(x + y) % 4])
# badge 3x3x1 @ (0,78): front face at (1,79)
PAT = [".#.", "###", "#.#"]
for y in range(3):
    for x in range(3):
        if PAT[y][x] != "#": continue
        c = GOLD_HI if (x, y) in ((1, 0), (1, 1)) else (GOLD_DK if y == 2 else GOLD)
        put(1 + x, 79 + y, c)

for sub in ("item", "entity"):
    img.save(os.path.join(RES, "textures", sub, "sheriff_hat.png"))
img.resize((512, 512), Image.NEAREST).save(os.path.join(HERE, "texture_preview.png"))

def r4(v): return round(v, 4)
def element(c):
    (x, y, z), (w, h, d), (gx, gy, gz), (u, v) = c["pos"], c["size"], c["grow"], c["uv"]
    frm = [r4(x-gx+8), r4(-(y+h+gy)-2), r4(z-gz+8)]
    to = [r4(x+w+gx+8), r4(-(y-gy)-2), r4(z+d+gz+8)]
    uv = lambda a: [r4(t / 8) for t in a]          # 128px atlas -> 16-unit UV space
    faces = {
        "north": [u+d, v+d, u+d+w, v+d+h], "south": [u+2*d+w, v+d, u+2*d+2*w, v+d+h],
        "west": [u, v+d, u+d, v+d+h],      "east": [u+d+w, v+d, u+2*d+w, v+d+h],
        "up": [u+d, v+d, u+d+w, v],        "down": [u+d+w, v, u+d+2*w, v+d]}
    return {"name": c["name"], "from": frm, "to": to,
            "faces": {k: {"uv": uv(a), "texture": "#0"} for k, a in faces.items()}}

DISPLAY = {
    "gui": {"rotation": [20, 210, 0], "translation": [0, 0, 0], "scale": [0.55, 0.55, 0.55]},
    "ground": {"rotation": [0, 0, 0], "translation": [0, 3, 0], "scale": [0.35, 0.35, 0.35]},
    "fixed": {"rotation": [0, 0, 0], "translation": [0, 0, 0], "scale": [0.55, 0.55, 0.55]},
    "thirdperson_righthand": {"rotation": [0, 0, 0], "translation": [0, 3, 1], "scale": [0.4, 0.4, 0.4]},
    "thirdperson_lefthand": {"rotation": [0, 0, 0], "translation": [0, 3, 1], "scale": [0.4, 0.4, 0.4]},
    "firstperson_righthand": {"rotation": [0, -45, 0], "translation": [0, 2.5, 0], "scale": [0.45, 0.45, 0.45]},
    "firstperson_lefthand": {"rotation": [0, 45, 0], "translation": [0, 2.5, 0], "scale": [0.45, 0.45, 0.45]},
}
def write_model(name, cubes):
    data = {"texture_size": [S, S],
            "textures": {"0": f"{MOD}:item/sheriff_hat", "particle": f"{MOD}:item/sheriff_hat"},
            "elements": [element(c) for c in cubes], "display": DISPLAY}
    json.dump(data, open(os.path.join(RES, "models", "item", name + ".json"), "w"), indent=2)
write_model("sheriff_hat", CUBES)
write_model("sheriff_hat_star", CUBES + [STAR])

# --- also emit the cube list for the web preview
json.dump({"cubes": [dict(uv=c["uv"], pos=c["pos"], size=c["size"], g=c["grow"]) for c in CUBES],
           "star": dict(uv=STAR["uv"], pos=STAR["pos"], size=STAR["size"], g=STAR["grow"])},
          open(os.path.join(HERE, "preview_cubes.json"), "w"))
print("assets generated")
