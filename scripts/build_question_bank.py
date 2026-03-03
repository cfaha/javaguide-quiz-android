#!/usr/bin/env python3
import json, re
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
JAVAGUIDE = ROOT / "imported" / "JavaGuide" / "docs"
OUT = ROOT / "content" / "questions" / "autogen-v0.json"


def stem_from_heading(h: str) -> str:
    h = re.sub(r"[`*#]+", "", h).strip()
    return f"关于『{h}』，下列说法最准确的是？"


def gen_from_md(path: Path, limit=None):
    text = path.read_text(encoding="utf-8", errors="ignore")
    heads = re.findall(r"^#{2,4}\s+(.+)$", text, flags=re.M)
    items = []
    cat = path.parts[1] if len(path.parts) > 1 else "general"
    selected = heads if limit is None else heads[:limit]
    for i, h in enumerate(selected, 1):
        qid = f"{cat}_{path.stem}_{i}".lower().replace(" ", "_")
        items.append({
            "id": qid,
            "type": "single",
            "category": cat,
            "subCategory": path.stem,
            "stem": stem_from_heading(h),
            "options": ["选项A", "选项B", "选项C", "选项D"],
            "answers": ["选项A"],
            "explanation": "自动生成草稿题，需人工审核答案与解析。",
            "difficulty": 2,
            "tags": [cat, path.stem],
            "source": {"repo": "Snailclimb/JavaGuide", "path": str(path.relative_to(ROOT / "imported" / "JavaGuide"))}
        })
    return items


def main(max_questions: int | None = None):
    if not JAVAGUIDE.exists():
        raise SystemExit("JavaGuide docs not found. Put repo under imported/JavaGuide")
    all_items = []
    for md in JAVAGUIDE.rglob("*.md"):
        all_items.extend(gen_from_md(md, limit=None))
        if max_questions is not None and len(all_items) >= max_questions:
            break
    OUT.parent.mkdir(parents=True, exist_ok=True)
    OUT.write_text(json.dumps(all_items, ensure_ascii=False, indent=2), encoding="utf-8")
    print(f"generated {len(all_items)} questions -> {OUT}")


if __name__ == "__main__":
    import argparse
    ap = argparse.ArgumentParser()
    ap.add_argument("--max", type=int, default=None, help="max questions")
    args = ap.parse_args()
    main(args.max)
