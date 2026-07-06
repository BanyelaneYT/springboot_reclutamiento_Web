from pathlib import Path

views = Path(r"src/main/webapp/WEB-INF/views")
for path in sorted(views.glob("*.jsp")):
    text = path.read_text(encoding="utf-8")
    cleaned = [line.rstrip() for line in text.splitlines() if line.strip()]
    new_text = "\n".join(cleaned) + "\n"
    path.write_text(new_text, encoding="utf-8", newline="\n")
    print(f"{path.name}: {len(text.splitlines())} -> {len(cleaned)}")
