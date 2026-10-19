import os
from PIL import Image, ImageDraw

base_dir = os.path.dirname(os.path.abspath(__file__))
assets_dir = os.path.join(base_dir, "common", "src", "main", "resources", "assets", "tactical_workbench")
block_tex_dir = os.path.join(assets_dir, "textures", "block")
item_tex_dir = os.path.join(assets_dir, "textures", "item")
block_models_dir = os.path.join(assets_dir, "models", "block")
item_models_dir = os.path.join(assets_dir, "models", "item")
blockstates_dir = os.path.join(assets_dir, "blockstates")

os.makedirs(block_tex_dir, exist_ok=True)
os.makedirs(item_tex_dir, exist_ok=True)
os.makedirs(block_models_dir, exist_ok=True)
os.makedirs(item_models_dir, exist_ok=True)
os.makedirs(blockstates_dir, exist_ok=True)

# 1. Generate Textures (16x16 pixel art)

# Workbench Top: dark industrial desk with green rubber cleaning mat and grid lines
img_top = Image.new("RGBA", (16, 16), (45, 38, 32, 255))
draw_top = ImageDraw.Draw(img_top)
# Edge border
draw_top.rectangle([0, 0, 15, 15], outline=(30, 25, 20, 255))
# Green tactical mat in center
draw_top.rectangle([2, 2, 13, 13], fill=(24, 48, 32, 255), outline=(15, 32, 20, 255))
# Mat grid lines
for x in (5, 8, 11):
    draw_top.line([(x, 3), (x, 12)], fill=(32, 60, 42, 255))
for y in (5, 8, 11):
    draw_top.line([(3, y), (12, y)], fill=(32, 60, 42, 255))
# Metal vice in corner
draw_top.rectangle([12, 1, 15, 4], fill=(70, 75, 80, 255), outline=(40, 45, 50, 255))
img_top.save(os.path.join(block_tex_dir, "tactical_workbench_top.png"))

# Workbench Front: drawers and metal handles
img_front = Image.new("RGBA", (16, 16), (55, 46, 38, 255))
draw_front = ImageDraw.Draw(img_front)
draw_front.rectangle([0, 0, 15, 15], outline=(35, 28, 22, 255))
# Drawer 1
draw_front.rectangle([2, 2, 13, 6], fill=(42, 34, 28, 255), outline=(28, 22, 18, 255))
draw_front.line([(6, 4), (9, 4)], fill=(120, 125, 130, 255)) # handle
# Drawer 2
draw_front.rectangle([2, 8, 13, 13], fill=(42, 34, 28, 255), outline=(28, 22, 18, 255))
draw_front.line([(6, 10), (9, 10)], fill=(120, 125, 130, 255)) # handle
# Corner bolts
for bx, by in [(1, 1), (14, 1), (1, 14), (14, 14)]:
    draw_front.point((bx, by), fill=(140, 145, 150, 255))
img_front.save(os.path.join(block_tex_dir, "tactical_workbench_front.png"))

# Workbench Side: tool pegboard and reinforced braces
img_side = Image.new("RGBA", (16, 16), (48, 40, 34, 255))
draw_side = ImageDraw.Draw(img_side)
draw_side.rectangle([0, 0, 15, 15], outline=(30, 25, 20, 255))
# Metal brackets
draw_side.rectangle([1, 1, 3, 14], fill=(60, 65, 70, 255))
draw_side.rectangle([12, 1, 14, 14], fill=(60, 65, 70, 255))
# Tools silhouette (wrench)
draw_side.line([(6, 3), (6, 11)], fill=(110, 115, 120, 255))
draw_side.point((5, 3), fill=(110, 115, 120, 255))
draw_side.point((7, 3), fill=(110, 115, 120, 255))
img_side.save(os.path.join(block_tex_dir, "tactical_workbench_side.png"))

# Workbench Bottom
img_bot = Image.new("RGBA", (16, 16), (35, 28, 22, 255))
img_bot.save(os.path.join(block_tex_dir, "tactical_workbench_bottom.png"))

# Tactical Rifle Item Texture
img_rifle = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
draw_rifle = ImageDraw.Draw(img_rifle)
# Barrel & Handguard
draw_rifle.line([(1, 7), (13, 7)], fill=(35, 37, 40, 255))
draw_rifle.line([(2, 8), (11, 8)], fill=(45, 48, 52, 255))
# Receiver
draw_rifle.rectangle([7, 6, 11, 9], fill=(50, 53, 58, 255))
# Stock
draw_rifle.rectangle([11, 7, 14, 10], fill=(30, 32, 35, 255))
# Magazine
draw_rifle.polygon([(8, 9), (7, 13), (9, 13), (9, 9)], fill=(20, 22, 24, 255))
# Grip
draw_rifle.polygon([(11, 9), (12, 12), (13, 12), (12, 9)], fill=(28, 30, 32, 255))
# Rail top highlight
draw_rifle.line([(7, 5), (10, 5)], fill=(80, 85, 90, 255))
img_rifle.save(os.path.join(item_tex_dir, "tactical_rifle.png"))

# Reflex Sight Texture
img_sight = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
draw_sight = ImageDraw.Draw(img_sight)
# Base mount
draw_sight.rectangle([4, 11, 11, 13], fill=(35, 38, 42, 255), outline=(20, 22, 25, 255))
# Hood / optic housing
draw_sight.rectangle([5, 4, 10, 10], outline=(45, 48, 52, 255))
# Tinted glass
draw_sight.rectangle([6, 5, 9, 9], fill=(60, 120, 140, 180))
# Red dot
draw_sight.point((7, 7), fill=(255, 30, 30, 255))
img_sight.save(os.path.join(item_tex_dir, "reflex_sight.png"))

# Suppressor Texture
img_sup = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
draw_sup = ImageDraw.Draw(img_sup)
# Cylindrical body
draw_sup.rectangle([2, 5, 13, 10], fill=(40, 42, 46, 255), outline=(25, 27, 30, 255))
# Texture grooves
for gx in (4, 7, 10):
    draw_sup.line([(gx, 6), (gx, 9)], fill=(60, 64, 70, 255))
# Thread collar
draw_sup.rectangle([13, 6, 14, 9], fill=(80, 85, 90, 255))
img_sup.save(os.path.join(item_tex_dir, "suppressor.png"))

# Vertical Grip Texture
img_grip = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
draw_grip = ImageDraw.Draw(img_grip)
# Picatinny clamp
draw_grip.rectangle([5, 2, 10, 5], fill=(50, 54, 58, 255), outline=(25, 27, 30, 255))
# Grip handle
draw_grip.rectangle([6, 5, 9, 13], fill=(32, 34, 38, 255), outline=(18, 20, 22, 255))
# Ribbed rubber lines
for gy in (7, 9, 11):
    draw_grip.line([(6, gy), (9, gy)], fill=(48, 52, 58, 255))
img_grip.save(os.path.join(item_tex_dir, "vertical_grip.png"))

# Gun Oil Texture
img_oil = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
draw_oil = ImageDraw.Draw(img_oil)
# Canister body
draw_oil.rectangle([4, 6, 11, 14], fill=(180, 150, 50, 255), outline=(120, 95, 30, 255))
# Label
draw_oil.rectangle([5, 8, 10, 11], fill=(30, 50, 90, 255))
# Spout
draw_oil.line([(7, 5), (7, 2)], fill=(150, 155, 160, 255))
draw_oil.line([(7, 2), (5, 1)], fill=(150, 155, 160, 255))
# Oil droplet
draw_oil.point((4, 2), fill=(230, 190, 40, 220))
img_oil.save(os.path.join(item_tex_dir, "gun_oil.png"))

print("All textures generated successfully.")
