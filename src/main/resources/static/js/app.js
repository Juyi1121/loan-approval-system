// app.js - 處理「送出申請」並導向或報錯
document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('f');
  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const companyId  = Number(document.getElementById('companyId').value);
    const loanAmount = Number(document.getElementById('loanAmount').value);
    const term       = Number(document.getElementById('term').value);
    const applicant  = document.getElementById('applicant').value.trim();

    try {
      const resp = await fetch('/api/applications', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ companyId, loanAmount, term, applicant })
      });

      if (!resp.ok) {
        throw new Error(`HTTP ${resp.status}`);
      }

      const data = await resp.json();
      alert(`送出成功！申請 ID：${data.id}`);
      window.location.href = `/loan/detail/${data.id}`; // ⭐ 修正：導向正確詳情頁
    } catch (err) {
      console.error(err);
      alert('送出失敗');
    }
  });
});