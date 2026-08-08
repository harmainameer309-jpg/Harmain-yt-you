document.addEventListener('DOMContentLoaded', () => {
  const urlInput = document.getElementById('urlInput');
  const btnClear = document.getElementById('btnClear');
  const btnPaste = document.getElementById('btnPaste');
  const platformBadge = document.getElementById('platformBadge');
  
  const mediaThumbnail = document.getElementById('mediaThumbnail');
  const mediaDuration = document.getElementById('mediaDuration');
  const mediaTitle = document.getElementById('mediaTitle');
  const mediaAuthor = document.getElementById('mediaAuthor');
  
  const btnDownload = document.getElementById('btnDownload');
  const downloadProgressBox = document.getElementById('downloadProgressBox');
  const dlStatus = document.getElementById('dlStatus');
  const dlSpeed = document.getElementById('dlSpeed');
  const dlProgressBar = document.getElementById('dlProgressBar');
  const dlBytes = document.getElementById('dlBytes');
  const dlEta = document.getElementById('dlEta');
  
  const toast = document.getElementById('toast');
  const formatChips = document.querySelectorAll('.format-chip');
  const platformCircles = document.querySelectorAll('.platform-circle');
  const navItems = document.querySelectorAll('.nav-item');

  let selectedQuality = '1080p';
  let selectedSize = '45.0MB';
  let isDownloading = false;

  // Toggle Clear button visibility
  urlInput.addEventListener('input', () => {
    btnClear.style.display = urlInput.value ? 'block' : 'none';
    detectPlatform(urlInput.value);
  });

  btnClear.addEventListener('click', () => {
    urlInput.value = '';
    btnClear.style.display = 'none';
    platformBadge.textContent = '🌐 Web Media';
  });

  btnPaste.addEventListener('click', async () => {
    if (urlInput.value) {
      extractMedia(urlInput.value);
    } else {
      try {
        const text = await navigator.clipboard.readText();
        if (text) {
          urlInput.value = text;
          btnClear.style.display = 'block';
          detectPlatform(text);
          extractMedia(text);
          showToast('Pasted link from clipboard!');
        }
      } catch (err) {
        showToast('Please type or paste link');
      }
    }
  });

  function detectPlatform(url) {
    const lower = url.toLowerCase();
    if (lower.includes('youtube.com') || lower.includes('youtu.be')) {
      platformBadge.textContent = '🎬 YouTube Detected';
    } else if (lower.includes('tiktok.com')) {
      platformBadge.textContent = '🎵 TikTok No-Watermark';
    } else if (lower.includes('instagram.com')) {
      platformBadge.textContent = '📷 Insta Reels 1080p';
    } else if (lower.includes('twitter.com') || lower.includes('x.com')) {
      platformBadge.textContent = '🐦 X / Twitter HD';
    } else if (lower.includes('soundcloud.com')) {
      platformBadge.textContent = '🎧 SoundCloud Lossless';
    } else {
      platformBadge.textContent = '🌐 Web Stream';
    }
  }

  function extractMedia(url) {
    showToast('Fetching media metadata...');
    setTimeout(() => {
      mediaTitle.textContent = 'Lofi Hip Hop Radio - Beats to Relax/Study to';
      mediaAuthor.textContent = 'Lofi Girl • 12K Watching';
      mediaDuration.textContent = '04:12';
      mediaThumbnail.src = 'https://images.unsplash.com/photo-1611162617474-5b21e879e113?q=80&w=600';
      showToast('Media metadata loaded!');
    }, 600);
  }

  formatChips.forEach(chip => {
    chip.addEventListener('click', () => {
      formatChips.forEach(c => c.classList.remove('selected'));
      chip.classList.add('selected');
      selectedQuality = chip.dataset.quality;
      selectedSize = chip.dataset.size;
      btnDownload.textContent = `DOWNLOAD ${selectedQuality.toUpperCase()} (${selectedSize})`;
    });
  });

  btnDownload.addEventListener('click', () => {
    if (isDownloading) return;
    isDownloading = true;
    downloadProgressBox.style.display = 'flex';
    
    let percent = 0;
    const totalSize = parseFloat(selectedSize);
    
    const interval = setInterval(() => {
      percent += 5;
      if (percent > 100) percent = 100;
      
      const currentMB = ((percent / 100) * totalSize).toFixed(1);
      const speed = (3.5 + Math.random() * 2).toFixed(1);
      const remainingSec = Math.max(0, Math.ceil((totalSize - currentMB) / speed));
      
      dlProgressBar.style.width = `${percent}%`;
      dlStatus.textContent = `Downloading... ${percent}%`;
      dlSpeed.textContent = `${speed} MB/s`;
      dlBytes.textContent = `${currentMB}MB / ${selectedSize}`;
      dlEta.textContent = `ETA: ${remainingSec}s`;
      
      if (percent >= 100) {
        clearInterval(interval);
        isDownloading = false;
        dlStatus.textContent = 'Completed 100%';
        dlEta.textContent = 'Saved!';
        showToast('Download complete! Saved to Library');
      }
    }, 200);
  });

  platformCircles.forEach(circle => {
    circle.addEventListener('click', () => {
      const url = circle.dataset.url;
      urlInput.value = url;
      btnClear.style.display = 'block';
      detectPlatform(url);
      extractMedia(url);
    });
  });

  navItems.forEach(item => {
    item.addEventListener('click', () => {
      navItems.forEach(i => i.classList.remove('active'));
      item.classList.add('active');
      showToast(`Switched to ${item.dataset.tab.toUpperCase()} tab`);
    });
  });

  function showToast(msg) {
    toast.textContent = msg;
    toast.classList.add('show');
    setTimeout(() => {
      toast.classList.remove('show');
    }, 2500);
  }
});
